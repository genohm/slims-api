/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import org.springframework.transaction.annotation.Transactional;

import com.genohm.slims.server.dao.criterion.SlimsRestrictions;
import com.genohm.slims.server.repository.queriers.ContentQueries;
import com.vaadin.flow.spring.annotation.SpringComponent;

@Transactional
@SpringComponent
public class SayHelloDataProvider {

	private final ContentQueries contentQueries;

	public SayHelloDataProvider(ContentQueries contentQueries) {
		this.contentQueries = contentQueries;
	}

	public Long countContent() {
		return contentQueries.count(SlimsRestrictions.alwaysTrue());
	}
}
