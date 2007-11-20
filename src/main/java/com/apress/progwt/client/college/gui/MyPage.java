package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.college.ToCollegeApp;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;

public class MyPage extends Composite implements TabListener {

    private ServiceCache serviceCache;

    private User thisUser;

    private TabPanel mainPanel;

    public MyPage(ToCollegeApp collegeBoundApp, User thisUser) {
        this.thisUser = thisUser;

        serviceCache = collegeBoundApp.getServiceCache();

        mainPanel = new TabPanel();

        MyRankings rankings = new MyRankings(serviceCache, thisUser);
        mainPanel.add(rankings, "My Rankings");

        MyApplications myApplications = new MyApplications(serviceCache,
                thisUser);
        mainPanel.add(myApplications, "My Applications");

        MyDecision myPriorities = new MyDecision(serviceCache, thisUser);
        mainPanel.add(myPriorities, "My Decision");

        mainPanel.addTabListener(this);
        initWidget(mainPanel);

        mainPanel.selectTab(0);

    }

    /**
     * intercept the event and give us a chance to refresh
     */
    public boolean onBeforeTabSelected(SourcesTabEvents sender,
            int tabIndex) {

        MyPageTab w = (MyPageTab) mainPanel.getWidget(tabIndex);
        w.refresh();

        return true;
    }

    public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
        MyPageTab w = (MyPageTab) mainPanel.getWidget(tabIndex);

        History.newItem("" + w.getHistoryName());
    }

}
