/*
 * Copyright 2022 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.genohm.slims.custom.CustomConfiguration;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SayHelloTest {

	@InjectMocks
	private SayHello sayHello;
	@Mock
	private CustomConfiguration customConfiguration;

	@Test
	public void happyPathTest() {

	}
	
}
