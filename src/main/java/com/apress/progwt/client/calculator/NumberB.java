package com.apress.progwt.client.calculator;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class NumberB extends CalcButton implements ClickListener {

	private String value;
	private Calculator calculator;

	public NumberB(Calculator calculator, String value) {
		super(value + "");
		this.value = value;
		this.calculator = calculator;
		addClickListener(this);
	}

	public NumberB(Calculator calculator, int i) {
		this(calculator, "" + i);
	}

	public void onClick(Widget sender) {
		calculator.digitAction(value);
	}

}
