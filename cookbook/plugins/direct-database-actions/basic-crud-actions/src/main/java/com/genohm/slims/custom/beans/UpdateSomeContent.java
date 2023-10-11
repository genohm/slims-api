/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import java.util.Arrays;
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

import com.genohm.slims.api.ConvertRecordService;
import com.genohm.slims.common.model.Content;
import com.genohm.slims.common.model.ContentMeta;
import com.genohm.slims.common.slimsgate.SlimsgateParameter;
import com.genohm.slims.common.util.DaoConstants;
import com.genohm.slims.common.util.StringUtil;
import com.genohm.slims.custom.BasicCrudActionsConfiguration;
import com.genohm.slims.server.dao.common.Dao;
import com.genohm.slims.server.repository.queriers.ContentRecordQueries;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateKeeperConstants;
import com.genohm.slimsgate.camel.gatekeeper.SlimsProxy;
import com.genohm.slimsgateclient.workflow.SlimsFlowInitParam;

@Component
public class UpdateSomeContent {

	@Autowired
	private BasicCrudActionsConfiguration basicCrudActionsConfiguration;

	@Autowired
	private ContentRecordQueries contentRecordQueries;

	@Autowired
	private ConvertRecordService convertRecordService;

	@Autowired
	private Dao<Content> contentDao;


	/**
	 * This method will fetch the content records that were checked off when the flow was started. It will then update
	 * their configured status field to the configured status.
	 */
	@Transactional
	@Handler
	public void createAContent(@Header(SlimsGateKeeperConstants.SLIMS_PROXY) SlimsProxy slimsProxy,
	                           @Header(SlimsGateKeeperConstants.SLIMS_WORKFLOW_INIT_PARAMETER) SlimsFlowInitParam slimsFlowInitParam) {

		// Get the PK's of the checked-off contents from slimsFlowInitParam and lookup those contents using the pk's
			// These PK's are a Collection of Longs - so we can cast the Object from the map returned by slimsFlowInitParam.getInputParameterValues() to a Set<>, List<>, etc.
		Set<Long> selectedContentPks = Arrays.stream(StringUtil.getAsLongArray(slimsFlowInitParam.getInputParameterValues().get(SlimsgateParameter.SLIMS_SELECT_SAMPLES))).collect(Collectors.toSet());
		List<Map<String, Object>> contentsToUpdate = contentRecordQueries.fetchIn(ContentMeta.PK,selectedContentPks);

		// Set each content to the configured "update" status display value and update the record
		for (Map<String, Object> content : contentsToUpdate) {
			Map<String, Object> contentCopy = new HashMap<>();
			// contentDao will identify the content to update using the PK of the record
				// Just put the PK and the fields we want to update into a map to avoid accidentally updating other fields
			contentCopy.put(ContentMeta.PK, content.get(ContentMeta.PK));
			contentCopy.put(ContentMeta.FK_STATUS, basicCrudActionsConfiguration.getUpdateStatusDisplayValue());
			Map<String, Object> internalFormatCopy = convertRecordService.convertToInternalFormat(contentCopy, DaoConstants.CONTENT);
			contentDao.update(internalFormatCopy);
		}
	}
	
}
