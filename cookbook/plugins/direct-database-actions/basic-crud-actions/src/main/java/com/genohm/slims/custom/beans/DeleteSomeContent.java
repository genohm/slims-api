/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.genohm.slims.common.model.Content;
import com.genohm.slims.common.model.ContentMeta;
import com.genohm.slims.common.model.ContentType;
import com.genohm.slims.common.request.SlimsOrder;
import com.genohm.slims.common.util.Optionals;
import com.genohm.slims.common.util.StringUtil;
import com.genohm.slims.custom.BasicCrudActionsConfiguration;
import com.genohm.slims.server.dao.common.Dao;
import com.genohm.slims.server.dao.criterion.SlimsRestrictions;
import com.genohm.slims.server.dao.request.FetchRequest;
import com.genohm.slims.server.dao.request.FetchRequestImpl;
import com.genohm.slims.server.repository.queriers.ContentQueries;
import com.genohm.slims.server.repository.queriers.ContentTypeQueries;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateErrorException;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateKeeperConstants;
import com.genohm.slimsgate.camel.gatekeeper.SlimsProxy;
import com.genohm.slimsgateclient.workflow.SlimsFlowInitParam;

@Component
public class DeleteSomeContent {

	@Autowired
	private BasicCrudActionsConfiguration basicCrudActionsConfiguration;

	@Autowired
	private ContentQueries contentQueries;

	@Autowired
	private ContentTypeQueries contentTypeQueries;

	@Autowired
	private Dao<Content> contentDao;

	private static final String FEEDBACK_KEY = "outputMessage";
	private static final String NUMBER_TO_DELETE_KEY = "numToDelete";


	/**
	 * This method will fetch X content records with the configured Content Type, sorted ascending or descending (depending on configuration).
	 *
	 * <p>
	 * X (the number to fetch) will be input by the user when they execute the flow. The returned information Map will note
	 * which contents were deleted
	 * </p>
	 *
	 * @return A map with a key "Information" whose value should be a String of HTML to be displayed back to the user
	 */
	@Transactional
	@Handler
	public Map<String, Object> deleteSomeContent(@Header(SlimsGateKeeperConstants.SLIMS_PROXY) SlimsProxy slimsProxy,
	                                          @Header(SlimsGateKeeperConstants.SLIMS_WORKFLOW_INIT_PARAMETER) SlimsFlowInitParam slimsFlowInitParam) {

		// User inputs from the step's <input> parameters will be stored in a Map in the SlimsFlowInitParam stored in a header in the route's Camel message
			// The keys will be the <input> elements' <name> values. Values will be the user input into that element
		Map<String, Object> userInputs = slimsFlowInitParam.getInputParameterValues();

		// Because datatype will vary amongst inputs, all values are generic Objects and will need to be converted when used
			// SLIMS' API contains some useful helper classes for datatype conversion. The most commonly-used methods will be in StringUtil
		Integer numToDelete = StringUtil.getAsInteger(userInputs.get(NUMBER_TO_DELETE_KEY));

		ContentType configuredContentType = Optionals.getOrThrow(
				contentTypeQueries.findByName(basicCrudActionsConfiguration.getContentTypeDisplayValue()),
				new SlimsGateErrorException(String.format("Could not find a Content Type with name %s", basicCrudActionsConfiguration.getContentTypeDisplayValue())));

		// Here we want to only fetch the ones we plan to delete. So we are going to make a standalone FetchRequest
			// This lets us build a FetchRequest object with settings to fine-tune the fetch like:
				// What field to sort results by, in what order
				// How many records to fetch at once
				// What criterion to use
				// Inclusion of custom fields
				// Inclusion of "display" values for FK fields
				// See dev manual for further examples/documentation
		FetchRequest contentFetchRequest = new FetchRequestImpl()
				.addOrder(basicCrudActionsConfiguration.getDeletionSortDescending() ? SlimsOrder.desc(basicCrudActionsConfiguration.getDeletionSortField()) : SlimsOrder.asc(basicCrudActionsConfiguration.getDeletionSortField()))
				.setPaged(true) // Means we will get a single "page" of records
				.setBatchSize(numToDelete) // Determines the number of records per page
				.setAddDisplayFields(false) // Do not fetch the "display" value for FK fields like Content Type -- we don't need them for deleting, and this speeds up the fetch
				.setFetchDynamicValues(false) // Do not fetch Custom Fields -- we don't need them for deleting, and this speeds up the fetch
				.setCriterion(SlimsRestrictions.equals(ContentMeta.FK_CONTENT_TYPE, configuredContentType.getCntp_pk()));

		List<Map<String, Object>> foundContents = contentDao.fetch(contentFetchRequest).getResultList();

		// Give the user an error popup if we could not find the requested number of contents to be deleted
		if(foundContents.size() < numToDelete) {
			throw new SlimsGateErrorException(String.format("User requested deletion of %s contents, but only found %s %s of configured type %s.",
					numToDelete,
					foundContents.size(),
					foundContents.size() == 1 ? "content" : "contents",
					configuredContentType.getCntp_name()));
		}

		// Delete the actual contents using contentDao.remove()
		foundContents.forEach(content -> {
			try {
				contentDao.remove(content);
			} catch (Exception e) {
				throw new SlimsGateErrorException(String.format("Encountered an error attempting to delete content %s. " +
						"Most likely this is an error from SLIMS itself that would also prevent manual deletion. " +
						"See stacktrace for details", content.get(ContentMeta.BAR_CODE)), e);
			}
		});

		// Create a return map with an HTML message containing the contents' barcodes to display to the user in the Feedback step
			// We store it in a key called "outputMessage" - to match the feedback step's configuration in slimsgate.xml
		Set<String> foundContentsBarcodes = foundContents.stream()
				.map(content -> StringUtil.getAsString(content.get(ContentMeta.BAR_CODE)))
				.collect(Collectors.toSet());
		Map<String, Object> returnMap = new HashMap<>();
		StringBuilder feedbackHtml = new StringBuilder(String.format("<p>Deleted the following %s %s with content type %s: <ul>",
				foundContentsBarcodes.size(),
				foundContents.size() == 1 ? "content" : "contents",
				basicCrudActionsConfiguration.getContentTypeDisplayValue()));
		foundContentsBarcodes.forEach(barcode -> feedbackHtml.append(String.format("<li>%s</li>", barcode)));
		feedbackHtml.append("</ul></p>");

		returnMap.put(FEEDBACK_KEY, feedbackHtml.toString());

		// Return our feedback map -- this map becomes the actual value stored in the <output> parameter of the Slimsgate.xml step that called this route
		return returnMap;
	}
	
}
