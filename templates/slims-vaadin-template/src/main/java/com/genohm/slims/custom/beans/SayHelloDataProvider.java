/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.genohm.slims.server.dao.criterion.SlimsRestrictions;
import com.genohm.slims.server.repository.queriers.ContentQueries;
import com.vaadin.spring.annotation.SpringComponent;

@Transactional
@SpringComponent
public class SayHelloDataProvider {

	@Autowired
	private ContentQueries contentQueries;

	public Long countContent() {
		return contentQueries.count(SlimsRestrictions.alwaysTrue());
	}
}
