package com.apress.progwt.client;

import com.apress.progwt.client.college.gui.ext.VerticalLabel;
import com.google.gwt.user.client.ui.RootPanel;

public class VerticalLabelApp extends GWTApp {

    public VerticalLabelApp(int pageID) {
        super(pageID);

        int total = Integer.parseInt(getParam("total"));

        for (int i = 0; i < total; i++) {
            String text = getParam("text" + i);
            String id = getParam("id" + i);

            RootPanel.get(id).add(new VerticalLabel(text));

        }
        RootPanel.get(getPreLoadID()).setVisible(false);
    }

}
