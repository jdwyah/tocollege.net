package com.apress.progwt.client.college.gui;

import java.util.List;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class MyApplications extends Composite implements MyPageTab {

    // private User thisUser;
    private ServiceCache serviceCache;
    private SimplePanel mainP;
    private Grid mainGrid;

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

        mainGrid = new Grid(schoolAndApps.size() + 1,
                processTypes.size() + 2);

        int row = 0;
        int col = 1;

        mainGrid.setWidget(0, col, new Label("Status"));
        col++;

        for (ProcessType processType : user.getNonStatusProcessTypes()) {
            mainGrid.setWidget(0, col, new Label(processType.getName()));
            col++;
        }

        row++;

        DeferredCommand.addCommand(new AddApplicationRows(schoolAndApps, user, row));

        mainP.setWidget(mainGrid);
    }

    /**
     * These methods can actually take quite a while on some browsers.
     * Let's use the IncrementalCommand to load 1 row at a time and free
     * up the browser in between.
     * 
     * The IncrementalCommand contract says that we'll keep getting calls
     * to execute() as long as we return true, so we just loop through the
     * applications and return false once we're done.
     * 
     * @author Jeff Dwyer
     * 
     */
    private class AddApplicationRows implements IncrementalCommand {

        private List<Application> applications;
        private int row;
        private int currentIndex = 0;
        private User user;

        public AddApplicationRows(List<Application> applications, User user, int row) {
            this.applications = applications;
            this.user = user;
            this.row = row;
        }

        public boolean execute() {

            if (currentIndex >= applications.size()) {
                return false;
            } else {
                int col = 0;
                Application application = applications.get(currentIndex);

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
                currentIndex++;
            }

            return true;
        }
    }
}
