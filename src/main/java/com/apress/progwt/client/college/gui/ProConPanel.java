package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.domain.SchoolAndAppProcess;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProConPanel extends Composite {

    private VerticalPanel pros;
    private VerticalPanel cons;

    public ProConPanel(User thisUser,
            SchoolAndAppProcess schoolAndApplication) {

        HorizontalPanel mainPanel = new HorizontalPanel();

        pros = new VerticalPanel();
        cons = new VerticalPanel();

        pros.add(new Label("sexy people"));
        pros.add(new Label("good food"));
        pros.addStyleName("ProConPanel");
        pros.addStyleName("ProConPanel-Pro");

        cons.add(new Label("not all sexy people"));
        cons.add(new Label("homework"));
        cons.addStyleName("ProConPanel");
        cons.addStyleName("ProConPanel-Con");

        mainPanel.add(pros);
        mainPanel.add(cons);

        initWidget(mainPanel);

    }

}
