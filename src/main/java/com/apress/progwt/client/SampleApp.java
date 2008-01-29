package com.apress.progwt.client;

import com.apress.progwt.client.calculator.Calculator;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SampleApp implements EntryPoint {

    public void onModuleLoad() {
        // Log.setUncaughtExceptionHandler();

        DeferredCommand.addCommand(new Command() {
            public void execute() {
                onModuleLoad2();
            }
        });
    }

    public void onModuleLoad2() {
        try {

            RootPanel.get("loading").setVisible(false);
            RootPanel.get("slot1").add(new Calculator());

        } catch (Exception e) {

            e.printStackTrace();

            VerticalPanel panel = new VerticalPanel();

            panel.add(new Label("Error"));
            panel.add(new Label(e.getMessage()));

            RootPanel.get("slot1").add(panel);
        }

    }
}
