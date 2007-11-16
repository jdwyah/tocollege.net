package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.domain.ApplicationAndScore;
import com.apress.progwt.client.domain.User;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SchoolRanks extends Composite {

    private static final NumberFormat pctFormat = NumberFormat
            .getPercentFormat();
    private VerticalPanel rankPanel;
    private User thisUser;

    public SchoolRanks(User thisUser) {
        this.thisUser = thisUser;
        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.add(new Label("Rankings"));

        rankPanel = new VerticalPanel();

        refresh();

        mainPanel.add(rankPanel);

        initWidget(mainPanel);
    }

    public void refresh() {
        rankPanel.clear();
        for (ApplicationAndScore aas : thisUser.getPrioritizedRankings()) {

            double pct = aas.getScore() / (double) aas.getTotal();

            rankPanel.add(new Label(aas.getApplication().getSchool()
                    .getName()
                    + " " + pctFormat.format(pct)));
        }
    }
}
