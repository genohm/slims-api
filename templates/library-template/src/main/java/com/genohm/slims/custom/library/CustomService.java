/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.genohm.slims.common.model.Content;
import com.genohm.slims.server.dao.common.Dao;

@Component
public class CustomService {

	@Autowired
	private Dao<Content> contentDao;

	@Transactional
	public void doSomething() {
		//TODO implement
	}

}
