/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.apress.progwt.client.college.gui;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.IndexedDropController;
import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.college.gui.ext.YesNoDialog;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.commands.RemoveSchoolFromRankCommand;
import com.apress.progwt.client.domain.commands.SaveSchoolRankCommand;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.rpc.EZCallback;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.apress.progwt.client.suggest.CompleteListener;
import com.apress.progwt.client.suggest.SchoolCompleter;
import com.apress.progwt.client.util.Utilities;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MyRankings extends Composite implements DragHandler,
        MyPageTab {

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

        entryDragController = new PickupDragController(RootPanel.get(),
                false);

        IndexedDropController rankDropController = new IndexedDropController(
                rankPanelPanel);
        entryDragController.registerDropController(rankDropController);

        entryDragController.addDragHandler(this);

        HorizontalPanel completerP = new HorizontalPanel();
        completer = new SchoolCompleter(serviceCache,
                new CompleteListener<School>() {
                    public void completed(School result) {
                        addSchool(result);
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

    public void addSchool(School school) {
        Application schoolAndApp = new Application(school);

        CollegeEntry entry = new CollegeEntry(user, schoolAndApp,
                serviceCache, this);
        int index = addEntry(entry);
        saveEntry(entry, index);
    }

    public void onDragEnd(DragEndEvent event) {
        Log.debug("DragEndEvent on: " + event);
        Log
                .debug("DragEndEvent on: "
                        + ((DragEndEvent) event).toString());

        try {

            CollegeEntry entry = (CollegeEntry) event.getSource();

            int index = rankPanelPanel.getWidgetIndex(entry);
            Log.debug("new index " + index);
            saveEntry(entry, index);

        } catch (ClassCastException e) {
            Log.error("MyPage: " + e);
        }

        Log.debug("event.getSource " + event.getSource().getClass());
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

        Log.debug("MyRankings.FOUND " + schoolAndApps.size()
                + " Schools ");

        for (Application schoolAndApp : schoolAndApps) {
            addEntry(new CollegeEntry(user, schoolAndApp, serviceCache,
                    this));
        }
        completeB.setEnabled(true);
        refreshRankings();
    }

    public void promptForDelete(final CollegeEntry collegeEntry) {
        YesNoDialog ynDialog = new YesNoDialog("Remove school?",
                "Really remove "
                        + collegeEntry.getApplication().getSchool()
                                .getName() + "?", new Command() {
                    public void execute() {
                        delete(collegeEntry);
                    }
                });
        ynDialog.center();
    }

    private void delete(final CollegeEntry collegeEntry) {
        RemoveSchoolFromRankCommand command = new RemoveSchoolFromRankCommand(
                collegeEntry.getApplication().getSchool(), user);
        serviceCache.executeCommand(command,
                new StdAsyncCallback<SiteCommand>("Remove Application") {
                    @Override
                    public void onSuccess(SiteCommand result) {
                        super.onSuccess(result);
                        rankPanelPanel.remove(collegeEntry);
                        rankedEntries.remove(collegeEntry);
                        refreshRankings();
                    }
                });
    }

}
