/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import org.springframework.beans.factory.annotation.Autowired;

import com.genohm.slims.custom.CustomConfiguration;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class SayHelloModel {

	@Autowired
	private SayHelloDataProvider dataProvider;

	@Autowired
	private CustomConfiguration customConfiguration;

	public String getInitialData() {
		return String.format("Hello from the SLIMS Vaadin template plugin, this is my configuration: parameterOne: %s, parameterTwo: %s",
				customConfiguration.getParameterOne(), customConfiguration.getParameterTwo());
	}

	public String getAdditionalData() {
		return String.format("Found %s Content in SLIMS",
				dataProvider.countContent());
	}
}
