package com.genohm.slims.custom.beans;

import org.springframework.beans.factory.annotation.Autowired;

import com.genohm.slims.common.model.Content;
import com.genohm.slims.custom.CustomConfiguration;
import com.genohm.slims.server.dao.common.Dao;
import com.genohm.slims.server.repository.queriers.ContentRecordQueries;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class SayHelloModel {

	@Autowired
	private Dao<Content> contentDao;

	@Autowired
	private ContentRecordQueries contentRecordQueries;

	@Autowired
	private CustomConfiguration customConfiguration;

	public String getInitialData() {
		return String.format("Hello from the slims vaadin template plugin, this is my configuration: parameterOne: %s, parameterTwo: %s",
				customConfiguration.getParameterOne(), customConfiguration.getParameterTwo());
	}

	public String getAdditionalData() {
		return "Hello world";
	}
}
