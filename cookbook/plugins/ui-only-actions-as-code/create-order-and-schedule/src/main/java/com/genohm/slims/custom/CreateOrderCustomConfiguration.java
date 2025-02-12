/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom;

public class CreateOrderCustomConfiguration {

	private String requestableUid;
	private String orderTypeUid;

	public CreateOrderCustomConfiguration() {

	}

	public String getRequestableUid() {
		return requestableUid;
	}
	public String getOrderTypeUid() {
		return orderTypeUid;
	}

	public static CreateOrderCustomConfiguration getDefault() {
		CreateOrderCustomConfiguration createOrderCustomConfiguration = new CreateOrderCustomConfiguration();

		// used to query for the desired requestable to add to content in the new order
		createOrderCustomConfiguration.requestableUid = "rqbl_wf_simplified_workflow_simplified_1";

		// used to look up the desired order type for the new order
		createOrderCustomConfiguration.orderTypeUid = "rdtp_default_workflow_order";
		return createOrderCustomConfiguration;
	}
}
