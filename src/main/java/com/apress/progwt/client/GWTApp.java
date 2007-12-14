package com.apress.progwt.client;

import com.apress.progwt.client.util.Logger;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GWTApp {

    private int pageID;

    public GWTApp(int pageID) {
        this.pageID = pageID;
    }

    protected String getLoadID() {
        return getLoadID(pageID);
    }

    protected String getPreLoadID() {
        return getPreLoadID(pageID);
    }

    private static String getLoadID(int id) {
        return "gwt-slot-" + id;
    }

    private static String getPreLoadID(int id) {
        return "gwt-loading-" + id;
    }

    protected void loadError(Exception e) {

        Logger.error("e: " + e);

        e.printStackTrace();

        VerticalPanel panel = new VerticalPanel();

        panel.add(new Label("Error"));
        panel.add(new Label(e.getMessage()));

        RootPanel.get(getPreLoadID()).setVisible(false);
        RootPanel.get(getLoadID()).add(panel);
    }

    protected void show(Widget panel) {
        show(pageID, panel);
    }

    public static void show(int id, Widget panel) {
        RootPanel.get(getPreLoadID(1)).setVisible(false);
        RootPanel.get(getLoadID(1)).add(panel);
    }
}
