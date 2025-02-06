/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import java.util.List;
import java.util.Map;

import com.genohm.slims.common.renderer.VaadinParameters;
import com.genohm.slims.custom.api.CustomUIRenderer;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class SayHelloRenderer implements CustomUIRenderer {

	private final SayHelloPresenter presenter;

	public SayHelloRenderer(SayHelloPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public Component render(Map<String, List<String>> parameters) {
		presenter.setWindowId(VaadinParameters.getParameterWithList(VaadinParameters.WINDOW_GUID, parameters));
		return presenter.getView();
	}

}
