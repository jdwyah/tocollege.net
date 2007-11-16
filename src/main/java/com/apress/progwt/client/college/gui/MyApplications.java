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

public class MyApplications extends Composite {

    private User thisUser;
    private ServiceCache serviceCache;

    public MyApplications(ServiceCache serviceCache, User thisUser) {
        this.thisUser = thisUser;
        this.serviceCache = serviceCache;

        List<ProcessType> processTypes = thisUser.getProcessTypes();

        List<Application> schoolAndApps = thisUser
                .getSchoolRankings();

        Grid mainGrid = new Grid(schoolAndApps.size() + 1, processTypes
                .size() + 1);

        int row = 0;
        int col = 1;
        for (ProcessType processType : thisUser.getProcessTypes()) {
            mainGrid.setWidget(0, col, new Label(processType.getName()));
            col++;
        }

        row++;
        for (Application schoolAndApp : schoolAndApps) {
            col = 0;

            mainGrid.setWidget(row, col, new Label(schoolAndApp
                    .getSchool().getName()));
            col++;

            for (ProcessType processType : thisUser.getProcessTypes()) {

                ProcessValue value = schoolAndApp.getProcess().get(
                        processType);
                if (value == null) {
                    value = new ProcessValue();
                }
                AppCheckboxWidget checkW = new AppCheckboxWidget(
                        processType, value, schoolAndApp, serviceCache);

                mainGrid.setWidget(row, col, checkW);
                col++;
            }

            row++;

        }

        initWidget(mainGrid);

    }

}
