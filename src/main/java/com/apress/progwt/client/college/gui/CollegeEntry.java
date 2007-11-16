package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CollegeEntry extends Composite {

    private Label collegeNameLabel;
    private FocusPanel fp;
    private Application application;
    private ServiceCache serviceCache;

    public CollegeEntry(User thisUser, Application application,
            ServiceCache serviceCache) {
        this.application = application;
        this.serviceCache = serviceCache;

        collegeNameLabel = new Label(application.getSchool().getName());

        VerticalPanel infoPanel = new VerticalPanel();

        infoPanel.add(collegeNameLabel);

        infoPanel.add(new CollegeRatingPanel(serviceCache, thisUser,
                application));
        infoPanel.add(new ProConPanel(thisUser, application));

        DisclosurePanel mainPanel = new DisclosurePanel(collegeNameLabel);
        mainPanel.add(infoPanel);
        mainPanel.setStyleName("TC-CollegEntry");

        // fp = new FocusPanel(mainPanel);
        initWidget(mainPanel);

    }

    public Application getSchoolAndApplication() {
        return application;
    }

    public Widget getDragHandle() {

        return collegeNameLabel;
    }
}
