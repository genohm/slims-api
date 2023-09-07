/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasicCrudActionsBuilder extends RouteBuilder {

	@Autowired
	private CreateAContent createAContent;

	@Autowired
	private FetchSomeContent fetchSomeContent;

	// TODO Route to update a content with the configured type to a new type
	// TODO Route to remove all contents of the configured type

	@Override
	public void configure() throws Exception {
		// This route will execute the @Handler-annotated method in the CreateAContent class
		from("direct:create-a-content")
				.bean(createAContent)
				.routeId("create-a-content");


		// This route will execute the @Handler-annotated method in the fetchSomeContent class
		from("direct:fetch-some-content")
				.bean(fetchSomeContent)
				.routeId("fetch-some-content");

		// This route doesn't have any beans - so it won't actually do anything
		// Can be used for "feedback" steps that don't need to od any logic
		from("direct:do-nothing")
				.log("Doing nothing")
				.routeId("do-nothing");
	}

}
