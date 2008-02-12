package com.apress.progwt.client.college.gui;

import java.util.List;

import com.allen_sauer.gwt.dragdrop.client.PickupDragController;
import com.allen_sauer.gwt.dragdrop.client.drop.IndexedDropController;
import com.apress.progwt.client.college.CollegeBoundApp;
import com.apress.progwt.client.college.SchoolCompleter;
import com.apress.progwt.client.college.TopicCache;
import com.apress.progwt.client.domain.ApplicationCheckbox;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.SchoolAndAppProcess;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MyPage extends Composite implements CompleteListener {

    private TopicCache serviceCache;
    private VerticalPanel rankPanelPanel;
    private User thisUser;
    private PickupDragController entryDragController;

    public MyPage(CollegeBoundApp collegeBoundApp, User thisUser) {
        this.thisUser = thisUser;

        VerticalPanel mainPanel = new VerticalPanel();
        rankPanelPanel = new VerticalPanel();

        entryDragController = new PickupDragController(null, false);

        IndexedDropController rankDropController = new IndexedDropController(
                rankPanelPanel);
        entryDragController.registerDropController(rankDropController);

        List<SchoolAndAppProcess> schoolAndApps = thisUser
                .getSchoolRankings();

        HorizontalPanel columnHeaders = new HorizontalPanel();
        for (ApplicationCheckbox column : thisUser
                .getApplicationStatusTypes()) {
            columnHeaders.add(new Label(column.getName()));
        }

        for (SchoolAndAppProcess schoolAndApp : schoolAndApps) {
            addEntry(new CollegeEntry(thisUser, schoolAndApp));
        }

        serviceCache = new TopicCache(collegeBoundApp);

        SchoolCompleter completer = new SchoolCompleter(serviceCache);

        completer.setCompleteListener(this);

        mainPanel.add(columnHeaders);
        mainPanel.add(rankPanelPanel);
        mainPanel.add(completer);

        initWidget(mainPanel);

    }

    private void addEntry(CollegeEntry entry) {

        entryDragController.makeDraggable(entry, entry.getDragHandle());
        rankPanelPanel.add(entry);
    }

    public void completed(School school) {
        SchoolAndAppProcess schoolAndApp = new SchoolAndAppProcess(
                school);

        CollegeEntry entry = new CollegeEntry(thisUser, schoolAndApp);
        addEntry(entry);
    }
}
