package com.apress.progwt.client.college.gui;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;

public class EqualSpacedPanel extends AbsolutePanel {

    private int curLeft = 0;
    private int curTop = 0;

    private int height;
    private int spacingPerEntry;

    private int ySpacing = 20;

    public EqualSpacedPanel(int height, int spacingPerEntry,
            int extraSpacing, int totalEntries) {
        super();
        this.height = height;
        this.spacingPerEntry = spacingPerEntry;

        setHeight(height + "px");
        setWidth((spacingPerEntry * totalEntries + extraSpacing) + "px");
    }

    public void add(String s) {

        Label label = new Label(s);

        Log.debug("EqualSpacedPanel Add " + s + " " + curLeft + " "
                + curTop);
        add(label, curLeft, curTop);

        curLeft += spacingPerEntry;
        curTop += ySpacing;
        if (curTop > height) {
            curTop = 0;
        }
    }

}
