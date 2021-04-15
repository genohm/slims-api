/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.genohm.slims.common.renderer.VaadinParameters;
import com.genohm.slims.custom.api.CustomUIRenderer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;

@UIScope
@SpringComponent
public class SayHelloRenderer implements CustomUIRenderer {

	@Autowired
	private SayHelloPresenter presenter;

	@Override
	public Component render(Map<String, String[]> parameters) {
		presenter.setWindowId(VaadinParameters.getParameter(VaadinParameters.WINDOW_GUID, parameters));
		return presenter.getView();
	}

}
