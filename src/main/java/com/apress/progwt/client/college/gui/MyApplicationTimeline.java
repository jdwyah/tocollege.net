package com.apress.progwt.client.college.gui;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.college.gui.timeline.ProcessTimeLineEntry;
import com.apress.progwt.client.college.gui.timeline.TimeLineObj;
import com.apress.progwt.client.college.gui.timeline.ZoomableTimeline;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

public class MyApplicationTimeline extends Composite implements MyPageTab {

    private SimplePanel mainP;

    // private User thisUser;
    private ServiceCache serviceCache;

    private ZoomableTimeline<ProcessTimeLineEntry> timeline;

    public MyApplicationTimeline(ServiceCache serviceCache) {

        this.serviceCache = serviceCache;

        mainP = new SimplePanel();

        timeline = new ZoomableTimeline<ProcessTimeLineEntry>(
                serviceCache, 700, 500);

        mainP.add(timeline);

        initWidget(mainP);

    }

    public void delete(Application application) {
        // TODO Auto-generated method stub

    }

    public String getHistoryName() {
        return "MyApplications";
    }

    public void load(User user) {

        List<ProcessType> processTypes = user.getNonStatusProcessTypes();

        List<Application> applications = user.getSchoolRankings();

        List<TimeLineObj<ProcessTimeLineEntry>> tlos = new ArrayList<TimeLineObj<ProcessTimeLineEntry>>();

        for (Application application : applications) {

            for (ProcessType processType : user
                    .getNonStatusProcessTypes()) {

                ProcessValue value = application.getProcess().get(
                        processType);

                ProcessTimeLineEntry pair = new ProcessTimeLineEntry(
                        application, processType, value);
                Log
                        .info("create pair " + processType + " value "
                                + value);

                TimeLineObj<ProcessTimeLineEntry> tlo = new TimeLineObj<ProcessTimeLineEntry>(
                        pair, pair);
                tlos.add(tlo);
            }
        }

        timeline.add(tlos);

    }

    public void refresh() {
        // TODO Auto-generated method stub

    }
}
