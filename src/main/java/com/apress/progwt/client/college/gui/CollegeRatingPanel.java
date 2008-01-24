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
