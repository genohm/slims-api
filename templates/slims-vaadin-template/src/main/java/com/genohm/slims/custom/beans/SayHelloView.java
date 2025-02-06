/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
public class SayHelloView {

	private final VerticalLayout leftSide = new VerticalLayout();
	private final VerticalLayout rightSide = new VerticalLayout();
	private final HorizontalLayout layout = new HorizontalLayout(leftSide, rightSide);
	private final TextArea textArea = new TextArea();

	private final Button actionButton;
	private final Button closeButton;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

	public SayHelloView() {
		actionButton = new Button("Click me");
		leftSide.add(actionButton);
		closeButton = new Button("Close");
		leftSide.add(closeButton);

		textArea.setMaxHeight(40.0f, Unit.EX);

		rightSide.add(new NativeLabel("Feedback"));
		rightSide.add(textArea);

		layout.setFlexGrow(1.0d, leftSide);
		layout.setFlexGrow(3.0d, rightSide);

		layout.setSizeFull();
		textArea.setSizeFull();
	}

	public void show(String s) {
		textArea.setValue(DATE_FORMAT.format(new Date()) + ": " + s + "\n" + textArea.getValue());
	}

	public Component getView() {
		return layout;
	}

	public void actionButtonClicked(Runnable runnable) {
		actionButton.addClickListener(event -> runnable.run());
	}

	public void closeButtonClicked(Runnable runnable) {
		closeButton.addClickListener(event -> runnable.run());
	}

}
