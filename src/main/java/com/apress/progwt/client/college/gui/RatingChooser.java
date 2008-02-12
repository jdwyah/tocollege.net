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
        mainPanel.setStylePrimaryName("RatingChooser");
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
            setStylePrimaryName("Rating");
            addMouseListener(this);
        }

        public void setSelected(boolean b) {
            if (b) {
                addStyleDependentName("Selected");
            } else {
                removeStyleDependentName("Selected");
            }
        }

        public int getValue() {
            return value;
        }

        public void onMouseUp(Widget sender, int x, int y) {
            select(value);
        }

        public void onMouseEnter(Widget sender) {
            addStyleDependentName("Hover");
        }

        public void onMouseLeave(Widget sender) {
            removeStyleDependentName("Hover");
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
