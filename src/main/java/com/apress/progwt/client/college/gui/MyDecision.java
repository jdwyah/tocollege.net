package com.apress.progwt.client.college.gui;

import java.util.List;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.RatingType;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MyDecision extends Composite {

    private ServiceCache serviceCache;
    private User thisUser;

    public MyDecision(ServiceCache serviceCache, User thisUser) {
        this.thisUser = thisUser;
        this.serviceCache = serviceCache;

        HorizontalPanel mainPanel = new HorizontalPanel();

        PriorityPanel priorityPanel = new PriorityPanel(thisUser);

        SchoolRanks schoolRanks = new SchoolRanks();

        mainPanel.add(priorityPanel);
        mainPanel.add(schoolRanks);

        initWidget(mainPanel);

    }

    private class SchoolRanks extends Composite {
        public SchoolRanks() {
            VerticalPanel mainPanel = new VerticalPanel();
            mainPanel.add(new Label("Rankings"));
            initWidget(mainPanel);
        }
    }

    private class PriorityPanel extends Composite {
        public PriorityPanel(User thisUser) {

            List<RatingType> ratings = thisUser.getRatingTypes();

            Grid mainGrid = new Grid(ratings.size() + 1, 2);

            mainGrid.setWidget(0, 0, new Label("Priority"));
            mainGrid.setWidget(0, 1, new Label("Weight"));

            int row = 1;
            for (RatingType ratingType : ratings) {
                mainGrid.setWidget(row, 0,
                        new Label(ratingType.getName()));

                int myPriority = thisUser.getPriority(ratingType);

                mainGrid.setWidget(row, 1, new RatingChooser(ratingType,
                        myPriority));

                row++;
            }

            initWidget(mainGrid);
        }
    }
}
