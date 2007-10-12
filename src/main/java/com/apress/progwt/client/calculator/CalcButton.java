package com.apress.progwt.client.calculator;

import com.google.gwt.user.client.ui.Button;

public class CalcButton extends Button {

	public CalcButton(String displayString) {
		super(displayString);
		addStyleName("CalcButton");
	}
}
