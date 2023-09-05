/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom;

public class BasicCrudActionsConfiguration {

	private String contentTypeDisplayValue;

	public BasicCrudActionsConfiguration() {

	}

	public String getContentTypeDisplayValue() {
		return contentTypeDisplayValue;
	}


	public static BasicCrudActionsConfiguration getDefault() {
		BasicCrudActionsConfiguration customConfiguration = new BasicCrudActionsConfiguration();
		customConfiguration.contentTypeDisplayValue = "Example Type";
		return customConfiguration;
	}

}
