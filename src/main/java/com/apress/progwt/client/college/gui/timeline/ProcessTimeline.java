package com.apress.progwt.client.college.gui.timeline;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.dnd.client.DragController;
import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.college.gui.MyApplicationTimeline;
import com.apress.progwt.client.college.gui.ProcessLabel;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.gui.timeline.BackdropListener;
import com.apress.progwt.client.gui.timeline.TimeLineObj;
import com.apress.progwt.client.gui.timeline.ZoomableTimeline;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

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
        BackdropListener, DragHandler {

    private ZoomableTimeline<ProcessTimeLineEntry> timeline;

    private Map<Application, List<TimeLineObj<ProcessTimeLineEntry>>> tlosByApplication = new HashMap<Application, List<TimeLineObj<ProcessTimeLineEntry>>>();

    private TimelineController controller;

    private DropController dropController;
    private PickupDragController dragController;

    public ProcessTimeline(MyApplicationTimeline controller) {
        this.controller = controller;
        timeline = new ZoomableTimeline<ProcessTimeLineEntry>(555, 500,
                controller);
        timeline.setBackdropListener(this);

        initWidget(timeline);

        dropController = new AbsolutePositionDropController(timeline);

        dragController = new PickupDragController(timeline, false);
        dragController.setBehaviorDragProxy(true);
        dragController.setBehaviorMultipleSelection(false);
        dragController.setBehaviorConstrainedToBoundaryPanel(true);

        dragController.registerDropController(dropController);
        dragController.addDragHandler(this);
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
                    TimeLineObj<ProcessTimeLineEntry> tlo = makeNew(
                            application, processType, value);
                    tlos.add(tlo);
                }
            }

            tlosByApplication.put(application, tlos);
        }
    }

    private TimeLineObj<ProcessTimeLineEntry> makeNew(
            Application application, ProcessType processType,
            ProcessValue value) {

        ProcessTimeLineEntry pair = new ProcessTimeLineEntry(application,
                processType, value);
        Log.info("create pair " + processType + " value " + value);

        TimeLineObj<ProcessTimeLineEntry> tlo = new TimeLineObj<ProcessTimeLineEntry>(
                pair, pair);
        return tlo;

    }

    public void addNew(Application application, ProcessType processType,
            ProcessValue value) {
        TimeLineObj<ProcessTimeLineEntry> tlo = makeNew(application,
                processType, value);

        Log.info("ProcessTimeline.addNew: " + application + " -> "
                + tlosByApplication.get(application));

        if (tlosByApplication.get(application) == null) {
            for (Application a : tlosByApplication.keySet()) {
                Log.debug("Found: ==" + a.equals(application) + "  A:"
                        + a + " ");
            }
        }

        tlosByApplication.get(application).add(tlo);

        showApplication(application);
    }

    public void showApplication(Application app) {
        List<TimeLineObj<ProcessTimeLineEntry>> tlos = tlosByApplication
                .get(app);

        timeline.showOnly(tlos);
    }

    public void onBackdropUserEvent(int x, int y) {

        Date date = timeline.getDateFromGUIX(x);

        controller.addProcess(date, x, y);

    }

    public DragController getDragController() {
        return dragController;
    }

    public void onDragEnd(DragEndEvent event) {

    }

    public void onDragStart(DragStartEvent event) {
    }

    /**
     * Always veto. We don't want to actually move the label onto the
     * timeline. We just want to knwo where it was dropped.
     */
    public void onPreviewDragEnd(DragEndEvent event)
            throws VetoDragException {
        Log.info("Drag End: " + event.getSource());
        if (event.getSource() instanceof ProcessLabel) {
            ProcessLabel lab = (ProcessLabel) event.getSource();
            int x = event.getContext().mouseX;
            Date date = timeline.getDateFromGUIX(x);

            controller.addProcess(lab.getProcessType(), date);
        }

        throw new VetoDragException();
    }

    public void onPreviewDragStart(DragStartEvent event)
            throws VetoDragException {
    }

    public void showStatus(TimeLineObj<ProcessTimeLineEntry> tlo) {

        ProcessTimeLineEntry pte = tlo.getObject();

        timeline.showStatus(new Label(pte.getProcessType().getName()));

    }
}