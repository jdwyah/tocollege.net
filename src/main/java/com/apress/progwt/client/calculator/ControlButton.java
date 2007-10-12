package com.apress.progwt.client.calculator;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class ControlButton extends CalcButton implements ClickListener {

	private ControlAction action;
	private Calculator calculator;

	public ControlButton(Calculator calculator, ControlAction action) {
		super(action.getDisplayString());
		this.action = action;
		this.calculator = calculator;

		addStyleName("ControlButton");
		addClickListener(this);
	}

	public void onClick(Widget sender) {
		calculator.actionClick(action);
	}
}
