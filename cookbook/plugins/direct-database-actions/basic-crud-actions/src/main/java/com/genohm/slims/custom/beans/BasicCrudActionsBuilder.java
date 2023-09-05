/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import org.apache.camel.builder.RouteBuilder;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasicCrudActionsBuilder extends RouteBuilder {

	@Autowired
	private CreateAContent createAContent;

	// TODO Route to fetch all contents with the configured type and log their barcodes
	// TODO Route to update a content with the configured type to a new type
	// TODO Route to remove all contents of the configured type

	@Override
	public void configure() throws Exception {
		// This route will execute the @Handler-annotated method in the CreateAContent class
		from("direct:create-a-content")
				.bean(createAContent)
				.routeId("create-a-content");


		//
	}

}
