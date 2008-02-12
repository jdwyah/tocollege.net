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

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.calculator.GUIEffects;
import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.college.gui.ext.RichTextToolbar;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.commands.AbstractCommand;
import com.apress.progwt.client.domain.commands.Orderable;
import com.apress.progwt.client.domain.commands.SaveApplicationCommand;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CollegeEntry extends Composite implements Orderable {

    private Label collegeNameLabel;
    private Label rankLabel;
    private FocusPanel fp;
    private Application application;
    private ServiceCache serviceCache;
    private ProConPanel proConPanel;
    private RichTextArea notesField;
    private User user;
    private MyRankings myRankings;
    private Button saveB;

    public CollegeEntry(User user, Application application,
            ServiceCache serviceCache, MyRankings myRankings) {
        this.application = application;
        this.serviceCache = serviceCache;
        this.user = user;
        this.myRankings = myRankings;

        collegeNameLabel = new Label(application.getSchool().getName());
        collegeNameLabel.setStylePrimaryName("TC-CollegeLabel");
        rankLabel = new Label();
        rankLabel.setStylePrimaryName("TC-CollegeEntry-RankLabel");

        HorizontalPanel mainPanel = new HorizontalPanel();
        mainPanel.add(rankLabel);
        mainPanel.add(collegeNameLabel);
        mainPanel.setCellWidth(rankLabel, "30px");

        DisclosurePanel disclosurePanel = new DisclosurePanel(" ");
        disclosurePanel.add(getInfoPanel());
        mainPanel.add(disclosurePanel);
        mainPanel.setCellWidth(disclosurePanel, "20px");
        mainPanel.setCellHorizontalAlignment(disclosurePanel,
                HorizontalPanel.ALIGN_RIGHT);
        mainPanel.setStylePrimaryName("TC-CollegeEntry");

        initWidget(mainPanel);

    }

    /**
     * What is displayed when the user expands us.
     * 
     * @return
     */
    private Widget getInfoPanel() {

        VerticalPanel infoPanel = new VerticalPanel();

        TabPanel infoTabs = new TabPanel();
        infoTabs.add(getThoughts(), "Thoughts");
        infoTabs.add(getRatings(), "Ratings");

        infoTabs.selectTab(1);

        infoPanel.add(infoTabs);
        infoPanel.add(getButtons());

        return infoPanel;
    }

    /**
     * get buttons to show under all tabs
     */
    private Widget getButtons() {
        HorizontalPanel buttonP = new HorizontalPanel();
        Button deleteB = new Button("Remove "
                + application.getSchool().getName() + " from MyRankings");
        deleteB.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                myRankings.promptForDelete(CollegeEntry.this);
            }
        });

        buttonP.add(deleteB);
        return buttonP;
    }

    /**
     * ratings tab
     * 
     * @return
     */
    private Widget getRatings() {
        CollegeRatingPanel ratingP = new CollegeRatingPanel(serviceCache,
                user, application);
        return ratingP;
    }

    /**
     * thoughts tab
     * 
     * @return
     */
    private Widget getThoughts() {
        proConPanel = new ProConPanel(user, application, this);

        notesField = new RichTextArea();
        notesField.setSize("30em", "15em");
        notesField.setHTML(application.getNotes());
        RichTextToolbar rtt = new RichTextToolbar(notesField);

        saveB = new Button("Save");
        saveB.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {

                Log.debug("CollegeEntry.bf " + application.getNotes()
                        + " " + application.getPros().size());
                bindFields();
                Log.debug("CollegeEntry.af " + application.getNotes()
                        + " " + application.getPros().size());

                AbstractCommand command = new SaveApplicationCommand(
                        application);
                serviceCache.executeCommand(command,
                        new StdAsyncCallback<SiteCommand>("Save") {
                        });
            }
        });

        VerticalPanel thoughtsP = new VerticalPanel();
        thoughtsP.add(rtt);
        thoughtsP.add(notesField);
        thoughtsP.add(proConPanel);
        thoughtsP.add(saveB);
        return thoughtsP;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    private void bindFields() {
        proConPanel.bindFields(application);
        application.setNotes(notesField.getHTML());
    }

    public Widget getDragHandle() {

        return collegeNameLabel;
    }

    public void setSortOrder(int order) {
        rankLabel.setText(order + "");
    }

    public void needsSave(boolean b) {
        if (b) {
            GUIEffects.highlight(saveB, "FFFE7F", "DDDDDD");
        }
    }
}
