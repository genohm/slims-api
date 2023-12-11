/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * There are cases where you might need to modify this file, but 99% of the time you can just copy it as-is
 */
@Configuration
@ComponentScan("com.genohm.slims.custom")
@ImportResource("classpath:spring/camel-context.xml")
public class SpringConfig {
	
}
