/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom;

public class BasicCrudActionsConfiguration {

	private String contentTypeDisplayValue;
	private boolean fetchRecordAsMaps;
	private String statusDisplayValue;

	public BasicCrudActionsConfiguration() {

	}

	public String getContentTypeDisplayValue() {
		return contentTypeDisplayValue;
	}
	public boolean getFetchRecordAsMaps() { return fetchRecordAsMaps; }
	public String getStatusDisplayValue() { return statusDisplayValue; }

	public static BasicCrudActionsConfiguration getDefault() {
		BasicCrudActionsConfiguration customConfiguration = new BasicCrudActionsConfiguration();
		customConfiguration.contentTypeDisplayValue = "Example Type";
		customConfiguration.statusDisplayValue = "Pending";
		customConfiguration.fetchRecordAsMaps = false;
		return customConfiguration;
	}

}
