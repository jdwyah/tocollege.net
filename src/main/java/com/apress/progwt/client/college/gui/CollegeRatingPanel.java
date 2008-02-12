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
import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.RatingType;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.commands.SaveRatingCommand;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CollegeRatingPanel extends Composite implements
        ChangeListener {

    private ServiceCache serviceCache;
    private Application application;

    public CollegeRatingPanel(ServiceCache serviceCache, User thisUser,
            Application application) {

        this.serviceCache = serviceCache;
        this.application = application;

        VerticalPanel mainPanel = new VerticalPanel();

        Grid ratingGrid = new Grid(thisUser.getRatingTypes().size(), 2);

        int row = 0;
        for (RatingType ratingType : thisUser.getRatingTypes()) {

            ratingGrid.setWidget(row, 0, new Label(ratingType.getName()));

            int rating = application.getRating(ratingType);

            RatingChooser chooser = new RatingChooser(ratingType, rating);
            chooser.addChangeListener(this);
            ratingGrid.setWidget(row, 1, chooser);

            row++;
        }
        mainPanel.add(ratingGrid);

        initWidget(mainPanel);
    }

    public void onChange(Widget sender) {

        Log.debug("CollegeRatingPanel On Change");

        if (sender instanceof RatingChooser) {
            RatingChooser chooser = (RatingChooser) sender;
            serviceCache.executeCommand(new SaveRatingCommand(chooser
                    .getRatingType(), chooser.getSelectedRating(),
                    application), new StdAsyncCallback<SiteCommand>(
                    "Rating Save"));
        }

    }

}
