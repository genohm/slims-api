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

	@Autowired
	private DeleteSomeContent deleteSomeContent;

	@Autowired
	private UpdateSomeContent updateSomeContent;


	@Override
	public void configure() {
		// This route will execute the @Handler-annotated method in the CreateAContent class
		from("direct:create-a-content")
				.bean(createAContent)
				.routeId("create-a-content");


		// This route will execute the @Handler-annotated method in the FetchSomeContent class
		from("direct:fetch-some-content")
				.bean(fetchSomeContent)
				.routeId("fetch-some-content");


		// This route will execute the @Handler-annotated method in the DeleteSomeContent class
		from("direct:delete-some-content")
				.bean(deleteSomeContent)
				.routeId("delete-some-content");


		// This route will execute the @Handler-annotated method in the UpdateSomeContent class
		from("direct:update-some-content")
				.bean(updateSomeContent)
				.routeId("update-some-content");


		// This route doesn't have any beans - so it won't actually do anything
		// Can be used for "feedback" steps that don't need to do any logic
		from("direct:do-nothing")
				.log("Doing nothing")
				.routeId("do-nothing");
	}

}
