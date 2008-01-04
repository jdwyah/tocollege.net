package com.apress.progwt.client.college.gui;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.dragdrop.client.DragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.DragHandler;
import com.allen_sauer.gwt.dragdrop.client.DragStartEvent;
import com.allen_sauer.gwt.dragdrop.client.IndexedDragEndEvent;
import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.VetoDragException;
import com.allen_sauer.gwt.dragdrop.client.drop.IndexedDropController;
import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.commands.SaveSchoolRankCommand;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.rpc.EZCallback;
import com.apress.progwt.client.suggest.CompleteListener;
import com.apress.progwt.client.suggest.SchoolCompleter;
import com.apress.progwt.client.util.Logger;
import com.apress.progwt.client.util.Utilities;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MyRankings extends Composite implements DragHandler,
        MyPageTab, CompleteListener<School> {

    private User user;
    private VerticalPanel rankPanelPanel;
    private List<CollegeEntry> rankedEntries = new ArrayList<CollegeEntry>();
    private PickupDragController entryDragController;
    private ServiceCache serviceCache;
    private SchoolCompleter completer;
    private Button completeB;

    public MyRankings(ServiceCache serviceCache) {

        this.serviceCache = serviceCache;

        VerticalPanel mainPanel = new VerticalPanel();
        rankPanelPanel = new VerticalPanel();

        entryDragController = new PickupDragController(null, false);

        IndexedDropController rankDropController = new IndexedDropController(
                rankPanelPanel);
        entryDragController.registerDropController(rankDropController);

        entryDragController.addDragHandler(this);

        HorizontalPanel completerP = new HorizontalPanel();
        completer = new SchoolCompleter(serviceCache,
                new CompleteListener<School>() {
                    public void completed(School result) {
                        // do something with School
                    }
                });
        completeB = new Button("Add School");
        completeB.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                completer.complete();
            }
        });
        completerP.add(completer);
        completerP.add(completeB);

        completeB.setEnabled(false);

        mainPanel.add(rankPanelPanel);
        mainPanel.add(completerP);

        initWidget(mainPanel);

    }

    private int addEntry(CollegeEntry entry) {

        entryDragController.makeDraggable(entry, entry.getDragHandle());

        int widgetCount = rankPanelPanel.getWidgetCount();
        rankPanelPanel.add(entry);
        rankedEntries.add(entry);
        return widgetCount;

    }

    private void saveEntry(final CollegeEntry entry, int rank) {

        SaveSchoolRankCommand comm = new SaveSchoolRankCommand(entry
                .getApplication().getSchool(), user, rank);

        serviceCache.executeCommand(comm, new EZCallback<SiteCommand>() {
            public void onSuccess(SiteCommand success) {
                Logger.debug("Success");

                SaveSchoolRankCommand rtn = (SaveSchoolRankCommand) success;
                entry.getApplication().setId(rtn.getSavedApplicationID());
            }
        });

        Utilities.reOrder(rankedEntries, entry, rank);

        refreshRankings();

    }

    private void refreshRankings() {
        int i = 1;
        for (CollegeEntry entry : rankedEntries) {
            entry.setSortOrder(i++);
        }

    }

    public void completed(School school) {
        Application schoolAndApp = new Application(school);

        CollegeEntry entry = new CollegeEntry(user, schoolAndApp,
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

    public void refresh() {
        // TODO Auto-generated method stub

    }

    public String getHistoryName() {
        return "MyRankings";
    }

    public void load(User user) {
        this.user = user;

        List<Application> schoolAndApps = user.getSchoolRankings();

        System.out.println("FOUND " + schoolAndApps.size() + " Schools ");

        for (Application schoolAndApp : schoolAndApps) {
            addEntry(new CollegeEntry(user, schoolAndApp, serviceCache));
        }
        completeB.setEnabled(true);
        refreshRankings();
    }

}
