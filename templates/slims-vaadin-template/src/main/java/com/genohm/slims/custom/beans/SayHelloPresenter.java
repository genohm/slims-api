/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import org.slf4j.Logger;

import com.genohm.slims.custom.api.SlimsLogger;
import com.genohm.slims.custom.api.WindowController;
import com.genohm.slims.server.dao.common.ActiveUser;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import jakarta.annotation.PostConstruct;

@UIScope
@SpringComponent
public class SayHelloPresenter {

	private final SayHelloView view;
	private final SayHelloModel model;
	private final WindowController windowController;

	private String windowId;

	private static final Logger LOG = SlimsLogger.getLogger(SayHelloPresenter.class);

	public SayHelloPresenter(SayHelloView view,
	                         SayHelloModel model,
	                         WindowController windowController) {
		this.view = view;
		this.model = model;
		this.windowController = windowController;
	}

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
