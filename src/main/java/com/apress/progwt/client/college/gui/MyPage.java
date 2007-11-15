package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.college.ToCollegeApp;
import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;

public class MyPage extends Composite {

    private ServiceCache serviceCache;

    private User thisUser;

    public MyPage(ToCollegeApp collegeBoundApp, User thisUser) {
        this.thisUser = thisUser;

        serviceCache = collegeBoundApp.getServiceCache();

        TabPanel mainPanel = new TabPanel();

        MyRankings rankings = new MyRankings(serviceCache, thisUser);
        mainPanel.add(rankings, "My Rankings");

        MyApplications myApplications = new MyApplications(serviceCache,
                thisUser);
        mainPanel.add(myApplications, "My Applications");

        MyDecision myPriorities = new MyDecision(serviceCache,
                thisUser);
        mainPanel.add(myPriorities, "My Decision");

        initWidget(mainPanel);

        mainPanel.selectTab(0);

    }

}
