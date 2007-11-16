package com.apress.progwt.client.college.gui;

import java.util.List;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.RatingType;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MyDecision extends Composite {

    private ServiceCache serviceCache;
    private User thisUser;
    private SchoolRanks schoolRanks;

    public MyDecision(ServiceCache serviceCache, User thisUser) {
        this.thisUser = thisUser;
        this.serviceCache = serviceCache;

        HorizontalPanel mainPanel = new HorizontalPanel();

        PriorityPanel priorityPanel = new PriorityPanel(thisUser);

        schoolRanks = new SchoolRanks(thisUser);

        mainPanel.add(priorityPanel);
        mainPanel.add(schoolRanks);

        initWidget(mainPanel);

    }

    private class PriorityPanel extends Composite implements
            ChangeListener {
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

                RatingChooser chooser = new RatingChooser(ratingType,
                        myPriority);
                mainGrid.setWidget(row, 1, chooser);

                chooser.addChangeListener(this);

                row++;
            }

            initWidget(mainGrid);
        }

        public void onChange(Widget sender) {
            if (sender instanceof RatingChooser) {
                RatingChooser choose = (RatingChooser) sender;

                thisUser.getPriorities().put(choose.getRatingType(),
                        choose.getSelectedRating());
                schoolRanks.refresh();
            }

        }
    }
}
