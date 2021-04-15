/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.genohm.slims.common.renderer.RendererDefinition;
import com.genohm.slims.common.slimsgate.SlimsgateFlowUsage;
import com.genohm.slims.custom.api.RendererRegistration;
import com.genohm.slims.custom.beans.SayHelloRenderer;

@Configuration
@ComponentScan("com.genohm.slims.custom")
public class SpringConfig {

	@Bean
	public RendererRegistration sayHelloRendererRegistration() {
		return RendererRegistration.create(
				RendererDefinition.create("slims-vaadin-template-example",
						"SayHello",
						"SayHello",
						"icons/add_data.png",
						SlimsgateFlowUsage.CONTENT_MANAGEMENT,
						null),
				SayHelloRenderer.class);
	}

}
