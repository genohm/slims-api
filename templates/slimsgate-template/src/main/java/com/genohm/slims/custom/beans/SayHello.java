/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.genohm.slims.custom.CustomConfiguration;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateKeeperConstants;
import com.genohm.slimsgate.camel.gatekeeper.SlimsProxy;

@Component
public class SayHello {

	@Autowired
	private CustomConfiguration customConfiguration;

	@Transactional
	@Handler
	public void sayHi(@Header(SlimsGateKeeperConstants.SLIMS_PROXY) SlimsProxy slimsProxy) {
		slimsProxy.getLogger(getClass())
			.info("Hello from the slimsgate template plugin, this is my configuration: \n" +
				"parameterOne: " + customConfiguration.getParameterOne() + "\n" +
				"parameterTwo: " + customConfiguration.getParameterTwo());
	}
	
}
