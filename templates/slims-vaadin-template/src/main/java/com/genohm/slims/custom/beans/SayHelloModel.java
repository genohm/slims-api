/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import com.genohm.slims.custom.CustomConfiguration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class SayHelloModel {

	private final SayHelloDataProvider dataProvider;
	private final CustomConfiguration customConfiguration;

	public SayHelloModel(SayHelloDataProvider dataProvider, CustomConfiguration customConfiguration) {
		this.dataProvider = dataProvider;
		this.customConfiguration = customConfiguration;
	}

	public String getInitialData() {
		return String.format("Hello from the SLIMS Vaadin template plugin, this is my configuration: parameterOne: %s, parameterTwo: %s",
				customConfiguration.getParameterOne(), customConfiguration.getParameterTwo());
	}

	public String getAdditionalData() {
		return String.format("Found %s Content in SLIMS",
				dataProvider.countContent());
	}
}
