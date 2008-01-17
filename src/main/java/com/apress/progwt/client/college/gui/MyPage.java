package com.apress.progwt.client.college.gui;

import java.util.Iterator;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.college.ToCollegeApp;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.map.MyCollegeMap;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class MyPage extends Composite implements TabListener,
        HistoryListener {

    private ServiceCache serviceCache;

    private User thisUser;

    private TabPanel mainPanel;

    private MyRankings myRankings;

    private MyApplications myApplications;

    private MyDecision myPriorities;

    private MyCollegeMap myCollegeMap;

    public MyPage(ToCollegeApp collegeBoundApp) {

        serviceCache = collegeBoundApp.getServiceCache();

        mainPanel = new TabPanel();

        myRankings = new MyRankings(serviceCache);
        mainPanel.add(myRankings, "My Rankings");

        myApplications = new MyApplications(serviceCache);

        mainPanel.add(myApplications, "My Applications");

        myPriorities = new MyDecision(serviceCache);
        mainPanel.add(myPriorities, "My Decision");

        myCollegeMap = new MyCollegeMap(serviceCache);
        mainPanel.add(myCollegeMap, "College Map");

        mainPanel.addTabListener(this);
        initWidget(mainPanel);

        mainPanel.selectTab(0);

        History.addHistoryListener(this);

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

    public void load(User user) {
        this.thisUser = user;
        myRankings.load(user);
        myApplications.load(user);
        myPriorities.load(user);
        myCollegeMap.load(user);
    }

    public void onHistoryChanged(String historyToken) {
        int i = 0;
        for (Iterator<Widget> iterator = mainPanel.iterator(); iterator
                .hasNext();) {
            MyPageTab tab = (MyPageTab) iterator.next();
            if (tab.getHistoryName().equals(historyToken)) {
                mainPanel.selectTab(i);
            }
            i++;
        }

    }
}
