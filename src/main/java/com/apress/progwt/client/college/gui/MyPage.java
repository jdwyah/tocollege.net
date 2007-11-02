package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.college.CollegeBoundApp;
import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;

public class MyPage extends Composite {

    private ServiceCache serviceCache;

    private User thisUser;

    public MyPage(CollegeBoundApp collegeBoundApp, User thisUser) {
        this.thisUser = thisUser;

        serviceCache = collegeBoundApp.getServiceCache();

        TabPanel mainPanel = new TabPanel();

        MyProcess process = new MyProcess(serviceCache, thisUser);
        mainPanel.add(process, "My Process");

        MyPriorities myPriorities = new MyPriorities(serviceCache,
                thisUser);
        mainPanel.add(myPriorities, "My Priorities");

        initWidget(mainPanel);

        mainPanel.selectTab(0);

    }

}
