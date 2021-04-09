/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.dto;

import java.io.Serializable;

public class Data implements Serializable {

	private String propertyOne;
	private int propertyTwo;

	public Data(String propertyOne, int propertyTwo) {
		this.propertyOne = propertyOne;
		this.propertyTwo = propertyTwo;
	}

	public String getPropertyOne() {
		return propertyOne;
	}

	public int getPropertyTwo() {
		return propertyTwo;
	}

}
