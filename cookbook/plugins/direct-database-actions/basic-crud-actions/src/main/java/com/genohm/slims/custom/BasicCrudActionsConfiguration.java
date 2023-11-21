/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom;

public class BasicCrudActionsConfiguration {

	private String contentTypeDisplayValue;
	private boolean fetchRecordsAsMaps;
	private String statusDisplayValue;
	private String deletionSortField;
	private boolean deletionSortDescending;
	private String updateStatusDisplayValue;

	public BasicCrudActionsConfiguration() {

	}

	public String getContentTypeDisplayValue() {
		return contentTypeDisplayValue;
	}
	public boolean getFetchRecordsAsMaps() { return fetchRecordsAsMaps; }
	public String getStatusDisplayValue() { return statusDisplayValue; }
	public String getDeletionSortField() { return deletionSortField; }
	public boolean getDeletionSortDescending() { return deletionSortDescending; }
	public String getUpdateStatusDisplayValue() { return updateStatusDisplayValue; }

	public static BasicCrudActionsConfiguration getDefault() {
		BasicCrudActionsConfiguration customConfiguration = new BasicCrudActionsConfiguration();
		customConfiguration.contentTypeDisplayValue = "Example Type";
		customConfiguration.statusDisplayValue = "Pending";
		customConfiguration.fetchRecordsAsMaps = false;
		customConfiguration.deletionSortField = "cntn_createdOn";
		customConfiguration.deletionSortDescending = false;
		customConfiguration.updateStatusDisplayValue = "Removed";
		return customConfiguration;
	}

}
