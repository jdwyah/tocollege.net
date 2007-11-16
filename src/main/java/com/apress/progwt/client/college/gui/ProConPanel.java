package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ProConPanel extends Composite {

    private ProConDispPanel pros;
    private ProConDispPanel cons;

    public ProConPanel(User thisUser, Application application) {

        HorizontalPanel mainPanel = new HorizontalPanel();

        pros = new ProConDispPanel("Pro", application.getPros());
        cons = new ProConDispPanel("Con", application.getCons());

        mainPanel.add(pros);
        mainPanel.add(cons);

        initWidget(mainPanel);

    }

}
