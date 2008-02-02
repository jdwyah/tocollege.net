package com.apress.progwt.client.college.gui;

import java.util.Date;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.college.gui.ext.ContextMenu;
import com.apress.progwt.client.college.gui.timeline.ProcessTimeline;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A MyPageTab tab that display our users application information.
 * Specifically the process information.
 * 
 * @author Jeff Dwyer
 * 
 */
public class MyApplicationTimeline extends Composite implements MyPageTab {

    // private User thisUser;
    private ServiceCache serviceCache;

    private ProcessTimeline timeline;

    private AppList schoolPanel;

    private ProcessTypePanel processTypePanel;

    private User lastUser;

    public MyApplicationTimeline(ServiceCache serviceCache) {

        this.serviceCache = serviceCache;

        HorizontalPanel mainP = new HorizontalPanel();
        VerticalPanel chooserP = new VerticalPanel();

        timeline = new ProcessTimeline(this);

        schoolPanel = new AppList();
        processTypePanel = new ProcessTypePanel();

        chooserP.add(schoolPanel);
        chooserP.add(processTypePanel);

        mainP.add(timeline);
        mainP.add(chooserP);

        initWidget(mainP);

    }

    /**
     * Hold all ProcessType and create new TLO's when they're chosen.
     * 
     * @author Jeff Dwyer
     * 
     */
    private class ProcessTypePanel extends Composite {
        private VerticalPanel mainPanel;
        private Date selectedDate;

        public ProcessTypePanel() {
            this(new Date());
        }

        public ProcessTypePanel(Date selectedDate) {
            this.selectedDate = selectedDate;
            mainPanel = new VerticalPanel();
            initWidget(mainPanel);
        }

        public void load(User user) {

            mainPanel.clear();
            mainPanel.add(new HTML("<hr>"));
            mainPanel.add(new Label("Process Types"));
            List<ProcessType> processTypes = user.getProcessTypes();

            for (final ProcessType processType : processTypes) {

                final Label processLabel = new Label(processType
                        .getName());
                processLabel.setStylePrimaryName("ProcessLabel");
                processLabel.addClickListener(new ClickListener() {
                    public void onClick(Widget sender) {
                        Log.info("create " + processType + " on date "
                                + selectedDate);
                    }
                });

                mainPanel.add(processLabel);
            }
        }

    }

    /**
     * Hold all Applications and set the timeline to display the chosen
     * applications.
     * 
     * @author Jeff Dwyer
     * 
     */
    private class AppList extends Composite {
        private VerticalPanel mainPanel;

        private Label selected;

        public AppList() {
            mainPanel = new VerticalPanel();
            initWidget(mainPanel);
        }

        public void load(User user) {
            lastUser = user;
            List<Application> applications = user.getSchoolRankings();

            mainPanel.clear();

            for (final Application app : applications) {

                final Label schoolL = new Label(app.getSchool().getName());

                schoolL.setStylePrimaryName("ApplicationSelector");
                schoolL.addClickListener(new ClickListener() {
                    public void onClick(Widget sender) {

                        if (selected != null) {
                            selected.removeStyleDependentName("selected");
                        }
                        selected = schoolL;
                        selected.addStyleDependentName("selected");
                        showApplication(app);

                    }
                });

                mainPanel.add(schoolL);
            }
        }

    }

    protected void showApplication(Application app) {
        timeline.showApplication(app);
    }

    public void delete(Application application) {
        // TODO Auto-generated method stub

    }

    public String getHistoryName() {
        return "MyApplications";
    }

    public void load(User user) {

        timeline.load(user);
        schoolPanel.load(user);
        processTypePanel.load(user);

    }

    public void refresh() {
        // TODO Auto-generated method stub

    }

    public void newProcessEvent(Date date, int x, int y) {

        ProcessTypePanel typePanel = new ProcessTypePanel(date);
        typePanel.load(lastUser);

        ContextMenu menu = new ContextMenu(typePanel, x, y);
        menu.show();
    }
}
