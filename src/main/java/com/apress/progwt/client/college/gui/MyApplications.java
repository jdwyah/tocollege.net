package com.apress.progwt.client.college.gui;

import java.util.List;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class MyApplications extends Composite implements MyPageTab {

    // private User thisUser;
    private ServiceCache serviceCache;
    private SimplePanel mainP;

    public MyApplications(ServiceCache serviceCache) {

        this.serviceCache = serviceCache;

        mainP = new SimplePanel();
        mainP.add(new Label("Loading"));

        initWidget(mainP);

    }

    public void refresh() {
        // TODO Auto-generated method stub

    }

    public String getHistoryName() {
        return "MyApplications";
    }

    public void load(User user) {

        List<ProcessType> processTypes = user.getNonStatusProcessTypes();

        List<Application> schoolAndApps = user.getSchoolRankings();

        Grid mainGrid = new Grid(schoolAndApps.size() + 1, processTypes
                .size() + 2);

        int row = 0;
        int col = 1;

        mainGrid.setWidget(0, col, new Label("Status"));
        col++;

        for (ProcessType processType : user.getNonStatusProcessTypes()) {
            mainGrid.setWidget(0, col, new Label(processType.getName()));
            col++;
        }

        row++;
        for (Application application : schoolAndApps) {
            col = 0;

            mainGrid.setWidget(row, col, new SchoolLink(application
                    .getSchool()));
            col++;

            ApplicationStatusChooserWidget statusChooser = new ApplicationStatusChooserWidget(
                    application, serviceCache);
            mainGrid.setWidget(row, col, statusChooser);
            col++;

            for (ProcessType processType : user
                    .getNonStatusProcessTypes()) {

                ProcessValue value = application.getProcess().get(
                        processType);
                if (value == null) {
                    value = new ProcessValue();
                }
                AppCheckboxWidget checkW = new AppCheckboxWidget(
                        processType, value, application, serviceCache);

                mainGrid.setWidget(row, col, checkW);
                col++;
            }

            row++;

        }

        mainP.setWidget(mainGrid);
    }

}
