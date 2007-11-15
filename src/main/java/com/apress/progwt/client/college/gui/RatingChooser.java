package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.domain.RatingType;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;

public class RatingChooser extends Composite implements MouseListener {

    private static final int X_SPACING = 12;
    private static final int ELEMENTS = 10;
    private static final int FUDGE = 10;
    private static final int HEIGHT = 25;
    private int selectedRating;
    private RatingType ratingType;

    public RatingChooser(RatingType ratingType, int rating) {
        this.ratingType = ratingType;
        this.selectedRating = rating;

        AbsolutePanel mainPanel = new AbsolutePanel();
        for (int i = 1; i <= ELEMENTS; i++) {
            Label digit = new Label(i + "");
            digit.addStyleName("Rating");
            if (i == selectedRating) {
                digit.addStyleName("Rating-Selected");
            }
            digit.addMouseListener(this);
            mainPanel.add(digit, i * X_SPACING, 0);
        }
        mainPanel.setPixelSize(X_SPACING * ELEMENTS + FUDGE, HEIGHT);
        mainPanel.setStyleName("RatingChooser");
        initWidget(mainPanel);
    }

    public void onMouseDown(Widget sender, int x, int y) {
        // TODO Auto-generated method stub

    }

    public void onMouseEnter(Widget sender) {
        sender.addStyleName("Rating-Hover");
    }

    public void onMouseLeave(Widget sender) {
        sender.removeStyleName("Rating-Hover");
    }

    public void onMouseMove(Widget sender, int x, int y) {
        // TODO Auto-generated method stub

    }

    public void onMouseUp(Widget sender, int x, int y) {
        // TODO Auto-generated method stub

    }

}
