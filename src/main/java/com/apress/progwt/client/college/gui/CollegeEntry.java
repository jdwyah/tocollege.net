package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.SchoolAndAppProcess;
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
    private SchoolAndAppProcess schoolAndApplication;
    private ServiceCache serviceCache;

    public CollegeEntry(User thisUser,
            SchoolAndAppProcess schoolAndApplication,
            ServiceCache serviceCache) {
        this.schoolAndApplication = schoolAndApplication;
        this.serviceCache = serviceCache;

        collegeNameLabel = new Label(schoolAndApplication.getSchool()
                .getName());

        VerticalPanel infoPanel = new VerticalPanel();

        infoPanel.add(collegeNameLabel);

        infoPanel.add(new CollegeRatingPanel(thisUser,
                schoolAndApplication));
        infoPanel.add(new ProConPanel(thisUser, schoolAndApplication));

        DisclosurePanel mainPanel = new DisclosurePanel(collegeNameLabel);
        mainPanel.add(infoPanel);
        mainPanel.setStyleName("TC-CollegEntry");

        // fp = new FocusPanel(mainPanel);
        initWidget(mainPanel);

    }

    public SchoolAndAppProcess getSchoolAndApplication() {
        return schoolAndApplication;
    }

    public Widget getDragHandle() {

        return collegeNameLabel;
    }
}
