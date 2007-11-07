package com.apress.progwt.client.college.gui;

import java.util.List;

import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.DragHandler;
import com.allen_sauer.gwt.dragdrop.client.DragStartEvent;
import com.allen_sauer.gwt.dragdrop.client.IndexedDragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.VetoDragException;
import com.allen_sauer.gwt.dragdrop.client.drop.IndexedDropController;
import com.apress.progwt.client.college.SchoolCompleter;
import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.SchoolAndAppProcess;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.util.Logger;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MyProcess extends Composite implements DragHandler,
        CompleteListener<School> {

    private static final int HEADER_SPACING = 50;
    private static final int HEADER_HEIGHT = 100;
    private static final int EXTRA_HEADER = 50;

    private User thisUser;
    private VerticalPanel rankPanelPanel;
    private PickupDragController entryDragController;
    private ServiceCache serviceCache;
    private ProcessHeaderPanel headerPanel;

    public MyProcess(ServiceCache serviceCache, User thisUser) {
        this.thisUser = thisUser;
        this.serviceCache = serviceCache;

        VerticalPanel mainPanel = new VerticalPanel();
        rankPanelPanel = new VerticalPanel();

        entryDragController = new PickupDragController(null, false);

        IndexedDropController rankDropController = new IndexedDropController(
                rankPanelPanel);
        entryDragController.registerDropController(rankDropController);

        entryDragController.addDragHandler(this);

        List<SchoolAndAppProcess> schoolAndApps = thisUser
                .getSchoolRankings();

        System.out.println("FOUND " + schoolAndApps.size() + " Schools ");

        headerPanel = new ProcessHeaderPanel(thisUser);

        for (SchoolAndAppProcess schoolAndApp : schoolAndApps) {
            addEntry(new CollegeEntry(thisUser, schoolAndApp,
                    serviceCache));
        }

        SchoolCompleter completer = new SchoolCompleter(serviceCache,
                this);

        mainPanel.add(headerPanel);
        mainPanel.add(rankPanelPanel);
        mainPanel.add(completer);

        initWidget(mainPanel);

    }

    private int addEntry(CollegeEntry entry) {

        entryDragController.makeDraggable(entry, entry.getDragHandle());

        int widgetCount = rankPanelPanel.getWidgetCount();
        rankPanelPanel.add(entry);

        return widgetCount;

    }

    private void saveEntry(CollegeEntry entry, int rank) {
        serviceCache.saveEntry(entry, rank);
    }

    public void completed(School school) {
        SchoolAndAppProcess schoolAndApp = new SchoolAndAppProcess(school);

        CollegeEntry entry = new CollegeEntry(thisUser, schoolAndApp,
                serviceCache);
        int index = addEntry(entry);
        saveEntry(entry, index);
    }

    private void addNewProcessType(ProcessType result) {
        System.out.println("Complete " + result);
        headerPanel.addProcess(result);
        // TODO save new process type
    }

    public void onDragEnd(DragEndEvent event) {
        System.out.println("DragEndEvent on: " + event);
        System.out.println("DragEndEvent on: "
                + ((DragEndEvent) event).toString());

        try {
            IndexedDragEndEvent indexedEvent = (IndexedDragEndEvent) event;
            CollegeEntry entry = (CollegeEntry) event.getSource();

            saveEntry(entry, indexedEvent.getIndex());

        } catch (ClassCastException e) {
            Logger.error("MyPage: " + e);
        }

        System.out.println("event.getSource "
                + event.getSource().getClass());
    }

    public void onDragStart(DragStartEvent event) {
    }

    public void onPreviewDragEnd(DragEndEvent event)
            throws VetoDragException {
    }

    public void onPreviewDragStart(DragStartEvent event)
            throws VetoDragException {
    }

    private class ProcessHeaderPanel extends Composite implements
            CompleteListener<ProcessType> {

        private EqualSpacedPanel columnHeaders;

        public ProcessHeaderPanel(User thisUser) {
            columnHeaders = new EqualSpacedPanel(HEADER_HEIGHT,
                    HEADER_SPACING, EXTRA_HEADER, thisUser
                            .getProcessTypes().size());

            System.out.println("Found "
                    + thisUser.getProcessTypes().size() + " types.");

            for (ProcessType processType : thisUser.getProcessTypes()) {
                columnHeaders.add(processType.getName());
            }

            // ProcessCompleter completer = new ProcessCompleter(
            // serviceCache, this);
            //

            columnHeaders.setStyleName("TC-ProcessTypes");
            initWidget(columnHeaders);
        }

        public void addProcess(ProcessType result) {
            columnHeaders.add(new Label(result.getName()));
        }

        public void completed(ProcessType result) {
            addNewProcessType(result);
        }

    }
}
