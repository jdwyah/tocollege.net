package com.apress.progwt.client.calculator;

import com.apress.progwt.client.GWTApp;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class CalculatorApp extends GWTApp {

    private void loadGUI(Widget widget) {
        RootPanel.get(getPreLoadID()).setVisible(false);
        RootPanel.get(getLoadID()).add(widget);
    }

    public CalculatorApp(int pageID) {
        super(pageID);
        try {

            initServices();

            setMeUp();

        } catch (Exception e) {
            loadError(e);
        }
    }

    private void setMeUp() {
        loadGUI(new Calculator());
    }

    private void initServices() {

    }

}
