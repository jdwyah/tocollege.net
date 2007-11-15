package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.domain.RatingType;
import com.apress.progwt.client.domain.SchoolAndAppProcess;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CollegeRatingPanel extends Composite {

    public CollegeRatingPanel(User thisUser,
            SchoolAndAppProcess schoolAndApplication) {

        VerticalPanel mainPanel = new VerticalPanel();

        for (RatingType ratingType : thisUser.getRatingTypes()) {
            HorizontalPanel hp = new HorizontalPanel();
            hp.add(new Label(ratingType.getName()));

            int rating = schoolAndApplication.getRating(ratingType);

            hp.add(new RatingChooser(ratingType, rating));
            mainPanel.add(hp);
        }

        initWidget(mainPanel);
    }

}
