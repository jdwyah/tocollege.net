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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class MyDecision extends Composite implements MyPageTab {

    private ServiceCache serviceCache;
    private User thisUser;
    private SchoolRanks schoolRanks;
    private PriorityPanel priorityPanel;

    public MyDecision(ServiceCache serviceCache) {

        this.serviceCache = serviceCache;

        HorizontalPanel mainPanel = new HorizontalPanel();

        priorityPanel = new PriorityPanel();

        schoolRanks = new SchoolRanks();

        mainPanel.add(priorityPanel);
        mainPanel.add(schoolRanks);

        initWidget(mainPanel);

    }

    private class PriorityPanel extends Composite implements
            ChangeListener {
        private SimplePanel mainP = new SimplePanel();

        public PriorityPanel() {
            mainP.add(new Label("Loading"));

            initWidget(mainP);
        }

        public void load(User user) {
            List<RatingType> ratings = user.getRatingTypes();

            Grid mainGrid = new Grid(ratings.size() + 1, 2);

            mainGrid.setWidget(0, 0, new Label("Priority"));
            mainGrid.setWidget(0, 1, new Label("Weight"));

            int row = 1;
            for (RatingType ratingType : ratings) {
                mainGrid.setWidget(row, 0,
                        new Label(ratingType.getName()));

                int myPriority = user.getPriority(ratingType);

                RatingChooser chooser = new RatingChooser(ratingType,
                        myPriority);
                mainGrid.setWidget(row, 1, chooser);

                chooser.addChangeListener(this);

                row++;
            }
            mainP.setWidget(mainGrid);
        }

        public void onChange(Widget sender) {
            if (sender instanceof RatingChooser) {
                RatingChooser choose = (RatingChooser) sender;

                thisUser.getPriorities().put(choose.getRatingType(),
                        choose.getSelectedRating());
                refresh();
            }

        }
    }

    public void refresh() {
        schoolRanks.refresh();
    }

    public String getHistoryName() {
        return "MyDecision";
    }

    public void load(User user) {
        thisUser = user;

        priorityPanel.load(user);
        schoolRanks.load(user);

    }
}
