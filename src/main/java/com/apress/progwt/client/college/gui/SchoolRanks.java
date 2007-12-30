package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.domain.ApplicationAndScore;
import com.apress.progwt.client.domain.User;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SchoolRanks extends Composite {

    private static final NumberFormat pctFormat = NumberFormat
            .getPercentFormat();
    private VerticalPanel rankPanel;
    private User thisUser;

    public SchoolRanks() {
        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.add(new Label("Rankings"));

        rankPanel = new VerticalPanel();

        mainPanel.add(rankPanel);

        initWidget(mainPanel);
    }

    public void refresh() {
        if (thisUser != null) {
            load(thisUser);
        }
    }

    public void load(User user) {
        thisUser = user;
        rankPanel.clear();
        int i = 1;
        for (ApplicationAndScore aas : thisUser.getPrioritizedRankings()) {

            double pct = aas.getScore() / (double) aas.getTotal();

            SchoolLink link = new SchoolLink(aas.getApplication()
                    .getSchool());

            Label pctL = new Label(pctFormat.format(pct));

            HorizontalPanel hP = new HorizontalPanel();
            hP.setStyleName("TC-DecisionRanked");
            if (i % 2 == 0) {
                hP.addStyleName("TC-DecisionRanked-Even");
            }

            Label rankL = new Label(i + "");
            rankL.addStyleName("TC-DecisionRanked-Rank");
            pctL.addStyleName("TC-DecisionRanked-Pct");

            hP.add(rankL);
            hP.add(link);
            hP.add(pctL);

            rankPanel.add(hP);
            i++;
        }
    }
}
