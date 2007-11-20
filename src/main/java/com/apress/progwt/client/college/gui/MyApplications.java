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

public class MyApplications extends Composite implements MyPageTab {

    private User thisUser;
    private ServiceCache serviceCache;

    public MyApplications(ServiceCache serviceCache, User thisUser) {
        this.thisUser = thisUser;
        this.serviceCache = serviceCache;

        List<ProcessType> processTypes = thisUser
                .getNonStatusProcessTypes();

        List<Application> schoolAndApps = thisUser.getSchoolRankings();

        Grid mainGrid = new Grid(schoolAndApps.size() + 1, processTypes
                .size() + 2);

        int row = 0;
        int col = 1;

        mainGrid.setWidget(0, col, new Label("Status"));
        col++;

        for (ProcessType processType : thisUser
                .getNonStatusProcessTypes()) {
            mainGrid.setWidget(0, col, new Label(processType.getName()));
            col++;
        }

        row++;
        for (Application application : schoolAndApps) {
            col = 0;

            mainGrid.setWidget(row, col, new Label(application
                    .getSchool().getName()));
            col++;

            ApplicationStatusChooserWidget statusChooser = new ApplicationStatusChooserWidget(
                    application, serviceCache);
            mainGrid.setWidget(row, col, statusChooser);
            col++;

            for (ProcessType processType : thisUser
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

        initWidget(mainGrid);

    }

    public void refresh() {
        // TODO Auto-generated method stub

    }

    public String getHistoryName() {
        return "MyApplications";
    }

}
