/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.genohm.slims.common.model.Content;
import com.genohm.slims.common.model.ContentMeta;
import com.genohm.slims.common.model.ContentType;
import com.genohm.slims.common.model.ContentTypeMeta;
import com.genohm.slims.common.util.Optionals;
import com.genohm.slims.common.util.StringUtil;
import com.genohm.slims.custom.BasicCrudActionsConfiguration;
import com.genohm.slims.server.dao.criterion.SlimsCriterion;
import com.genohm.slims.server.dao.criterion.SlimsRestrictions;
import com.genohm.slims.server.repository.queriers.ContentQueries;
import com.genohm.slims.server.repository.queriers.ContentRecordQueries;
import com.genohm.slims.server.repository.queriers.ContentTypeQueries;
import com.genohm.slims.server.repository.queriers.ContentTypeRecordQueries;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateErrorException;

@Component
public class FetchSomeContent {

	@Autowired
	private BasicCrudActionsConfiguration basicCrudActionsConfiguration;

	@Autowired
	private ContentRecordQueries contentRecordQueries;

	@Autowired
	private ContentQueries contentQueries;

	@Autowired
	private ContentTypeRecordQueries contentTypeRecordQueries;

	@Autowired
	private ContentTypeQueries contentTypeQueries;

	private static final String FEEDBACK_KEY = "outputMessage";


	/**
	 * This method will fetch all of the content records with the configured Content Type as its Type and return some feedback with their barcodes
	 *
	 * @return A map with a key "Information" whose value should be a String of HTML to be displayed back to the user
	 */
	@Transactional
	@Handler
	public Map<String, Object> fetchSomeContent() {
		List<String> foundContentsBarcodes = new ArrayList<>();

		// If the configuration parameter fetchRecordAsMaps is true - we will use <someTable>RecordQueries instead of <someTable>Queries
			// XyzRecordQueries return the records as Map<String, Objects> instead of custom classes like Content, ContentType, etc.
			// The keys in records fetched as Maps<> will be SLIMS field names, and the values will be the value that record has in that field
			// It is easier to work with the Custom classes, but if you need values from Custom Fields, the custom classes don't have getters for those fields - you have to fetch the records as Maps<>
		if(basicCrudActionsConfiguration.getFetchRecordsAsMaps()) {
			try {
				// These criteria can be defined inline on the actual fetch as well
				SlimsCriterion contentTypeFetchCriteria = SlimsRestrictions.and(
						SlimsRestrictions.equals(ContentTypeMeta.NAME, basicCrudActionsConfiguration.getContentTypeDisplayValue()),
						SlimsRestrictions.equals(ContentTypeMeta.ACTIVE, true));

				// Fetch the content type
					// Here we use fetchOneStrict(), under the assumption that there should only be one content type with the configured "name" value
				// When fetching an Optional<>, we can use Optionals.getOrThrow() to check if the Optional<> is empty and, if so, throw an error. Otherwise, returns Optional.get()
					// Optionals is a SLIMS-api class, but there are similar methods in standard java
				Map<String, Object> configuredContentType = Optionals.getOrThrow(
						contentTypeRecordQueries.fetchOneStrict(contentTypeFetchCriteria),
						new SlimsGateErrorException(String.format("Could not find any Content Types with name %s", basicCrudActionsConfiguration.getContentTypeDisplayValue())));

				// Fetch all the contents in the database with the configured Content Type
				// A regular fetch() will return a List with any records meeting the provided criteria
				List<Map<String, Object>> foundContents = contentRecordQueries.fetch(SlimsRestrictions.equals(
						ContentMeta.FK_CONTENT_TYPE, // When fetching based on a Foreign-key relation, you have to look for the presence of the target record's Primary Key (pk)
						configuredContentType.get(ContentTypeMeta.PK)));

				// Loop over each found Content map and add its barcode to foundContentsBarcodes
				// StringUtil is a SLIMS API utility with several useful methods for safely attempting to cast Objects into other datatypes
					// There is also MapUtil, SetUtil, BigDecimalUtil, and others that can be found within the platform-api-common-A.B.C.jar External Libraries
				foundContents.forEach(content -> foundContentsBarcodes.add(StringUtil.getAsString(content.get(ContentMeta.BAR_CODE))));
			} catch (Optionals.TooManyElementsException e) {
				throw new SlimsGateErrorException(String.format("Found more than one Content Type whose %s field value is %s", ContentTypeMeta.NAME, basicCrudActionsConfiguration.getContentTypeDisplayValue()), e);
			}
		} else { // If we have configured the plugin not to fetch the records as maps
			// Here we use contentTypeQueries instead of contentTypeRecordQueries -> so this will return ContentType objects instead of generic Map<String, Object>
				// These have the benefit of sometimes having more convenient fetch methods, and sometimes working with a dedicated class is more clear
				/// **** IMPORTANTLY THOUGH **** these fetched records' field values are accessed with getter methods, and they don't include Custom Fields
			ContentType configuredContentType = Optionals.getOrThrow(
					contentTypeQueries.findByName(basicCrudActionsConfiguration.getContentTypeDisplayValue()),
					new SlimsGateErrorException(String.format("Could not find a Content Type with name %s", basicCrudActionsConfiguration.getContentTypeDisplayValue())));

			// Lookup all the Content records with our configured type's Primary Key in the content's Foreign Key field that points to Content Type
			List<Content> foundContents = contentQueries.fetch(SlimsRestrictions.equals(
					ContentMeta.FK_CONTENT_TYPE, // When fetching based on a Foreign-key relation, you have to look for the presence of the target record's Primary Key (pk)
					configuredContentType.getCntp_pk()));

			// Add each content's barcode to the list of content barcodes
			foundContents.forEach(content -> foundContentsBarcodes.add(content.getCntn_barCode()));
		}


		// Create a return map with an HTML message containing the contents' barcodes to display to the user in the Feedback step
			// We store it in a key called "outputMessage" - to match the feedback step's configuration in slimsgate.xml
		Map<String, Object> returnMap = new HashMap<>();
		StringBuilder feedbackHtml = new StringBuilder(String.format("<p>Found %d contents with content type %s: <ul>", foundContentsBarcodes.size(), basicCrudActionsConfiguration.getContentTypeDisplayValue()));
		foundContentsBarcodes.forEach(barcode -> feedbackHtml.append(String.format("<li>%s</li>", barcode)));
		feedbackHtml.append("</ul></p>");

		returnMap.put(FEEDBACK_KEY, feedbackHtml.toString());

		// Return our feedback map -- this map becomes the actual value stored in the <output> parameter of the slimsgate.xml step that called this route
		return returnMap;
	}
	
}
