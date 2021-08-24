/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.genohm.slims.custom.api.SlimsLogger;
import com.genohm.slims.custom.api.WindowController;
import com.genohm.slims.server.dao.common.ActiveUser;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;

@UIScope
@SpringComponent
public class SayHelloPresenter {

	@Autowired
	private SayHelloView view;
	@Autowired
	private SayHelloModel model;

	@Autowired
	private WindowController windowController;

	private String windowId;

	private static final Logger LOG = SlimsLogger.getLogger(SayHelloPresenter.class);

	@PostConstruct
	public void postConstruct() {
		try {
			String initialData = model.getInitialData();
			view.show(initialData);

			view.actionButtonClicked(() -> {
				String additionalData = model.getAdditionalData();
				view.show(additionalData);
			});

			view.closeButtonClicked(() ->
					windowController.close(windowId, ActiveUser.get().getUser_pk().toString()));
		} catch (Exception e) {
			LOG.error("Error during postConstruct", e);
			throw e;
		}
	}

	public Component getView() {
		return view.getView();
	}

	public void setWindowId(String windowId) {
		this.windowId = windowId;
	}
}
