package com.apress.progwt.client.calculator;


import com.apress.progwt.client.util.Logger;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CalculatorApp {

	public static final String MAIN_DIV = "slot1";


	private void loadGUI(Widget widget) {
		RootPanel.get("loading").setVisible(false);
		RootPanel.get(MAIN_DIV).add(widget);
	}

	public CalculatorApp() {
		try {

			initServices();

			setMeUp();


		} catch (Exception e) {
			error(e);
		}
	}

	private void setMeUp() {
		loadGUI(new Calculator());
	}

	private void initServices() {

	}

	protected void error(Exception e) {

		Logger.log("e: " + e);

		e.printStackTrace();

		VerticalPanel panel = new VerticalPanel();

		panel.add(new Label("Error"));
		panel.add(new Label(e.getMessage()));

		RootPanel.get("loading").setVisible(false);
		RootPanel.get("slot1").add(panel);
	}
}
