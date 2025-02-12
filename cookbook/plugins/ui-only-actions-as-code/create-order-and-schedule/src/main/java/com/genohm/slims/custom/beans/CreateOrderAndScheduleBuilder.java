/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderAndScheduleBuilder extends RouteBuilder {

	@Autowired CreateOrderAndSchedule createOrderAndSchedule;

	@Override
	public void configure() throws Exception {
		from("direct:create-order-and-schedule")
			.bean(createOrderAndSchedule)
			.routeId("create-order-and-schedule");
	}

}