/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom;

public class CustomConfiguration {

	private String parameterOne;
	private String parameterTwo;

	public CustomConfiguration() {

	}

	public String getParameterOne() {
		return parameterOne;
	}

	public String getParameterTwo() {
		return parameterTwo;
	}

	public static CustomConfiguration getDefault() {
		CustomConfiguration customConfiguration = new CustomConfiguration();
		customConfiguration.parameterOne = "Value 1";
		customConfiguration.parameterTwo = "Value 2";
		return customConfiguration;
	}

}
