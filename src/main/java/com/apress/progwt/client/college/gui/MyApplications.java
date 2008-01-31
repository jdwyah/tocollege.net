package com.apress.progwt.client.college.gui;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.college.gui.ext.VerticalLabel;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.User;
import com.google.gwt.gears.core.client.GearsException;
import com.google.gwt.gears.workerpool.client.MessageHandler;
import com.google.gwt.gears.workerpool.client.WorkerPool;
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

        List<Application> applications = user.getSchoolRankings();

        mainGrid = new Grid(applications.size() + 1,
                processTypes.size() + 2);

        int row = 0;
        int col = 1;

        mainGrid.setWidget(0, col, new Label("Status"));
        col++;

        for (ProcessType processType : user.getNonStatusProcessTypes()) {
            mainGrid.setWidget(0, col, new VerticalLabel(processType
                    .getName()));
            col++;
        }
        // doWorkerPoolDemo();

        row++;

        DeferredCommand.addCommand(new AddApplicationRows(applications,
                user, row));

        mainP.setWidget(mainGrid);
    }

    /**
     * This is an empty bit of code that just shows what a WorkerPool
     * might look like. This isn't a great fit for this class, becasue
     * Worker's can't have any access to the DOM.
     */
    private void doWorkerPoolDemo() {
        WorkerPool wp = null;
        try {
            wp = new WorkerPool(new MessageHandler() {

                public void onMessageReceived(String message,
                        int srcWorker) {
                    Log.info("Message: " + message + " src:" + srcWorker);
                }
            });

            wp.createWorkerFromString("");

        } catch (GearsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

        public AddApplicationRows(List<Application> applications,
                User user, int row) {
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

    public void delete(Application application) {
        // TODO Auto-generated method stub

    }
}
