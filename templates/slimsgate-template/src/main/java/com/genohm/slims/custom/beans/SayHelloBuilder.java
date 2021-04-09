/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SayHelloBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("direct:hello")
			.to("bean:sayHello")
			.routeId("sayHello");
	}

}
