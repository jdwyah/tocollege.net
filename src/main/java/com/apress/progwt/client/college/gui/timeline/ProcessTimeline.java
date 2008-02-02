package com.apress.progwt.client.college.gui.timeline;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.college.gui.MyApplicationTimeline;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.gui.timeline.BackdropListener;
import com.apress.progwt.client.gui.timeline.TimeLineObj;
import com.apress.progwt.client.gui.timeline.ZoomableTimeline;
import com.google.gwt.user.client.ui.Composite;

/**
 * Wrap a generic ZoomableTimeline with funtionality for displaying
 * ProcessTypes & Values.
 * 
 * ZoomableTimeline is just a generic display of TimeLineObj. This is the
 * class that should know what to do to translate our domain into the
 * timeline and react to events from the timeline.
 * 
 * 
 * 
 * @author Jeff Dwyer
 * 
 */
public class ProcessTimeline extends Composite implements
        BackdropListener {

    private ZoomableTimeline<ProcessTimeLineEntry> timeline;

    private Map<Application, List<TimeLineObj<ProcessTimeLineEntry>>> tlosByApplication = new HashMap<Application, List<TimeLineObj<ProcessTimeLineEntry>>>();

    private MyApplicationTimeline controller;

    public ProcessTimeline(MyApplicationTimeline controller) {
        this.controller = controller;
        timeline = new ZoomableTimeline<ProcessTimeLineEntry>(700, 500,
                new ProcessTimeLineObjFactory());
        timeline.setBackdropListener(this);

        initWidget(timeline);

    }

    public void load(User user) {

        List<Application> applications = user.getSchoolRankings();

        for (Application application : applications) {

            List<TimeLineObj<ProcessTimeLineEntry>> tlos = new ArrayList<TimeLineObj<ProcessTimeLineEntry>>();

            for (ProcessType processType : application.getProcess()
                    .keySet()) {

                ProcessValue value = application.getProcess().get(
                        processType);

                // value == null simply means that the user isn't using
                // that value yet.
                if (value != null) {
                    ProcessTimeLineEntry pair = new ProcessTimeLineEntry(
                            application, processType, value);
                    Log.info("create pair " + processType + " value "
                            + value);

                    TimeLineObj<ProcessTimeLineEntry> tlo = new TimeLineObj<ProcessTimeLineEntry>(
                            pair, pair);
                    tlos.add(tlo);
                }
            }

            tlosByApplication.put(application, tlos);

        }
    }

    public void showApplication(Application app) {
        List<TimeLineObj<ProcessTimeLineEntry>> tlos = tlosByApplication
                .get(app);

        timeline.showOnly(tlos);
    }

    public void onBackdropUserEvent(int x, int y) {

        Date date = timeline.getDateFromGUIX(x);

        controller.newProcessEvent(date, x, y);

    }
}