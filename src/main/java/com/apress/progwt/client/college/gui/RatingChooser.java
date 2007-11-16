package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.domain.RatingType;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.Widget;

public class RatingChooser extends Composite implements
        SourcesChangeEvents {

    private static final int X_SPACING = 12;
    private static final int ELEMENTS = 10;
    private static final int FUDGE = 20;
    private static final int HEIGHT = 25;
    private int selectedRating;
    private RatingType ratingType;
    private AbsolutePanel mainPanel;
    private ChangeListenerCollection changeListeners;

    public RatingChooser(RatingType ratingType, int selectedRating) {
        this.ratingType = ratingType;
        this.selectedRating = selectedRating;

        mainPanel = new AbsolutePanel();
        for (int i = 1; i <= ELEMENTS; i++) {
            Digit digit = new Digit(i);
            if (i == selectedRating) {
                digit.setSelected(true);

            }

            mainPanel.add(digit, i * X_SPACING, 0);
        }
        mainPanel.setPixelSize(X_SPACING * ELEMENTS + FUDGE, HEIGHT);
        mainPanel.setStyleName("RatingChooser");
        initWidget(mainPanel);
    }

    public int getSelectedRating() {
        return selectedRating;
    }

    public RatingType getRatingType() {
        return ratingType;
    }

    private void select(int value) {
        ((Digit) mainPanel.getWidget(selectedRating - 1))
                .setSelected(false);
        ((Digit) mainPanel.getWidget(value - 1)).setSelected(true);

        selectedRating = value;

        if (changeListeners != null) {
            changeListeners.fireChange(this);
        }
    }

    private class Digit extends Label implements MouseListener {

        private int value;

        public Digit(int value) {
            super(value + "");
            this.value = value;
            setStyleName("Rating");
            addMouseListener(this);
        }

        public void setSelected(boolean b) {
            if (b) {
                addStyleName("Rating-Selected");
            } else {
                removeStyleName("Rating-Selected");
            }
        }

        public int getValue() {
            return value;
        }

        public void onMouseUp(Widget sender, int x, int y) {
            select(value);
        }

        public void onMouseEnter(Widget sender) {
            addStyleName("Rating-Hover");
        }

        public void onMouseLeave(Widget sender) {
            removeStyleName("Rating-Hover");
        }

        public void onMouseMove(Widget sender, int x, int y) {

        }

        public void onMouseDown(Widget sender, int x, int y) {
        }

    }

    public void addChangeListener(ChangeListener listener) {
        if (changeListeners == null) {
            changeListeners = new ChangeListenerCollection();
        }
        changeListeners.add(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        if (changeListeners != null) {
            changeListeners.remove(listener);
        }
    }

}
