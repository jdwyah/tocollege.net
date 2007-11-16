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
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.util.Logger;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MyRankings extends Composite implements DragHandler,
        CompleteListener<School> {

    private User thisUser;
    private VerticalPanel rankPanelPanel;
    private PickupDragController entryDragController;
    private ServiceCache serviceCache;
    private SchoolCompleter completer;

    public MyRankings(ServiceCache serviceCache, User thisUser) {
        this.thisUser = thisUser;
        this.serviceCache = serviceCache;

        VerticalPanel mainPanel = new VerticalPanel();
        rankPanelPanel = new VerticalPanel();

        entryDragController = new PickupDragController(null, false);

        IndexedDropController rankDropController = new IndexedDropController(
                rankPanelPanel);
        entryDragController.registerDropController(rankDropController);

        entryDragController.addDragHandler(this);

        List<Application> schoolAndApps = thisUser.getSchoolRankings();

        System.out.println("FOUND " + schoolAndApps.size() + " Schools ");

        for (Application schoolAndApp : schoolAndApps) {
            addEntry(new CollegeEntry(thisUser, schoolAndApp,
                    serviceCache));
        }

        HorizontalPanel completerP = new HorizontalPanel();
        completer = new SchoolCompleter(serviceCache, this);
        Button completeB = new Button("Add School");
        completeB.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                completer.complete();
            }
        });
        completerP.add(completer);
        completerP.add(completeB);

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
        Application schoolAndApp = new Application(school);

        CollegeEntry entry = new CollegeEntry(thisUser, schoolAndApp,
                serviceCache);
        int index = addEntry(entry);
        saveEntry(entry, index);
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

}
