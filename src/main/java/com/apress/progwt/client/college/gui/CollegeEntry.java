package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.commands.Orderable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CollegeEntry extends Composite implements Orderable {

    private Label collegeNameLabel;
    private Label rankLabel;
    private FocusPanel fp;
    private Application application;
    private ServiceCache serviceCache;

    public CollegeEntry(User thisUser, Application application,
            ServiceCache serviceCache) {
        this.application = application;
        this.serviceCache = serviceCache;

        collegeNameLabel = new Label(application.getSchool().getName());
        rankLabel = new Label();
        rankLabel.setStyleName("TC-CollegeEntry-RankLabel");

        VerticalPanel infoPanel = new VerticalPanel();

        infoPanel.add(collegeNameLabel);

        infoPanel.add(new CollegeRatingPanel(serviceCache, thisUser,
                application));
        infoPanel.add(new ProConPanel(thisUser, application));

        DisclosurePanel disclosurePanel = new DisclosurePanel(" ");
        disclosurePanel.add(infoPanel);

        HorizontalPanel mainPanel = new HorizontalPanel();
        mainPanel.add(rankLabel);
        mainPanel.add(collegeNameLabel);
        mainPanel.add(disclosurePanel);
        mainPanel.setStyleName("TC-CollegEntry");

        initWidget(mainPanel);

    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Widget getDragHandle() {

        return collegeNameLabel;
    }

    public void setSortOrder(int order) {
        rankLabel.setText(order + "");
    }
}
