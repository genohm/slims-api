/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.genohm.slims.api.ConvertRecordService;
import com.genohm.slims.common.model.Content;
import com.genohm.slims.common.model.ContentMeta;
import com.genohm.slims.common.util.DaoConstants;
import com.genohm.slims.custom.BasicCrudActionsConfiguration;
import com.genohm.slims.server.dao.common.Dao;

@Component
public class CreateAContent {

	@Autowired
	private BasicCrudActionsConfiguration basicCrudActionsConfiguration;

	@Autowired
	private Dao<Content> contentDao;

	@Autowired
	private ConvertRecordService convertRecordService;


	/**
	 * This method will create a content record with the configured Content Type as its Type
	 * <p>
	 * Please note, if your instance has required custom fields, this code will need to be modified
	 * so that the content map being created includes those fields
	 * </p>
	 */
	@Transactional
	@Handler
	public void createAContent() {
		// Create an empty map. We will add keys to the map that are the names of SLIMS fields
		Map<String, Object> contentToCreate = new HashMap<>();

		// Add the configured content type display value to the map at the Content Type key
			// The value should be what would appear on the content in the Content Type field when you've selected the type you want
		contentToCreate.put(ContentMeta.FK_CONTENT_TYPE, basicCrudActionsConfiguration.getContentTypeDisplayValue());
		// Do the same for the default required Status field on Content
		contentToCreate.put(ContentMeta.FK_STATUS, basicCrudActionsConfiguration.getStatusDisplayValue());

		// Use convertRecordsService to convert the keys in our map to the correct datatype for import into SLIMS
			// See ConvertRecordService javadoc for thorough explanation of how it works and how it expects data to be formatted
		// Use DaoConstants to get the name of the Database Table for a given type of record
		Map<String, Object> convertedContent = convertRecordService.convertToInternalFormat(contentToCreate, DaoConstants.CONTENT);

		// Create the content in SLIMS, this method returns a Map<String, Object> of the created record (content)
		Map<String, Object> contentCreated = contentDao.add(convertedContent);
	}
	
}
