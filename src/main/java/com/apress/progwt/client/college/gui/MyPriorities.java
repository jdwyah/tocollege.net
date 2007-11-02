package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MyPriorities extends Composite {

    private ServiceCache serviceCache;
    private User thisUser;

    public MyPriorities(ServiceCache serviceCache, User thisUser) {
        this.thisUser = thisUser;
        this.serviceCache = serviceCache;

        HorizontalPanel mainPanel = new HorizontalPanel();

        PriorityPanel priorityPanel = new PriorityPanel();

        SchoolRanks schoolRanks = new SchoolRanks();

        mainPanel.add(priorityPanel);
        mainPanel.add(schoolRanks);

        initWidget(mainPanel);

    }

    private class SchoolRanks extends Composite {
        public SchoolRanks() {
            VerticalPanel mainPanel = new VerticalPanel();
            mainPanel.add(new Label("Rankings"));
            initWidget(mainPanel);
        }
    }

    private class PriorityPanel extends Composite {
        public PriorityPanel() {
            VerticalPanel mainPanel = new VerticalPanel();

            HorizontalPanel headerP = new HorizontalPanel();

            headerP.add(new Label("Priority"));
            headerP.add(new Label("Weight"));

            mainPanel.add(headerP);
            initWidget(mainPanel);
        }

    }
}
