package com.apress.progwt.client.college.gui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;

public class RatingChooser extends Composite implements MouseListener {

    public RatingChooser() {
        HorizontalPanel mainPanel = new HorizontalPanel();
        for (int i = 0; i < 10; i++) {
            Label digit = new Label(i + "");
            digit.addStyleName("Rating");
            digit.addMouseListener(this);
            mainPanel.add(digit);
        }
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
