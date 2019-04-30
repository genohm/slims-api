package com.genohm.slims.custom.beans;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.genohm.slims.custom.api.CustomUIRenderer;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;

import com.genohm.slims.custom.CustomConfiguration;
import com.vaadin.ui.Label;

@UIScope
@SpringComponent
public class SayHelloRenderer implements CustomUIRenderer {

	@Autowired
	private CustomConfiguration customConfiguration;

	@Override
	public Component render(Map<String, String[]> parameters) {
		Label label = new Label();
		label.setContentMode(ContentMode.HTML);
		label.setValue("Hello from the slimsgate template plugin, this is my configuration: <br>" +
				"parameterOne: " + customConfiguration.getParameterOne() + "<br>" +
				"parameterTwo: " + customConfiguration.getParameterTwo());
		return label;
	}

}
