/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom;

/**
 * <p>This class dictates the YAML configuration you can use to set the values of variables after uploading the plugin to your instance.</p>
 *
 * <p>Any class-level variable with a getter method (getMyVariableName()) will appear as a key in the YAML configuration, and when the plugin's routes are executed,
 * the corresponding value in the YAML will be used as the current value of that property. The getters do not have to
 * simply return the variable - the methods can include whatever logic you need.</p>
 *
 * <p>Please use these configuration classes to avoid "magic numbers" and "magic strings" in your code to reference things that can be changed
 * by end users in the SLIMS UI. You can reference the Development manual for further examples.</p>
 */
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
