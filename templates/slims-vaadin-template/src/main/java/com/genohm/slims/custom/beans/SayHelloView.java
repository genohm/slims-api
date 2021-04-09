/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringComponent
public class SayHelloView {

	private Layout leftSide = new VerticalLayout();
	private Layout rightSide = new VerticalLayout();
	private HorizontalLayout layout = new HorizontalLayout(leftSide, rightSide);
	private TextArea textArea = new TextArea();

	private final Button actionButton;
	private final Button closeButton;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

	public SayHelloView() {
		actionButton = new Button("Click me");
		leftSide.addComponent(actionButton);
		closeButton = new Button("Close");
		leftSide.addComponent(closeButton);

		textArea.setRows(20);

		rightSide.addComponent(new Label("Feedback"));
		rightSide.addComponent(textArea);

		layout.setExpandRatio(leftSide, 1);
		layout.setExpandRatio(rightSide, 3);

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
