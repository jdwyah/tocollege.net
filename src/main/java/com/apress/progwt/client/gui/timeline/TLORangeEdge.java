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
package com.apress.progwt.client.gui.timeline;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;

public class TLORangeEdge extends Composite implements MouseListener {

    private static final DateTimeFormat format = DateTimeFormat
            .getFormat("MMM, d yyyy");

    private boolean resizing;
    private Image myLabel;
    private int resizeStartX;

    private ZoomableTimeline timeline;
    private boolean leftSide;
    private TimeLineObj tlo;
    private TimelineRemembersPosition rp;

    // private TooltipListener tooltip;

    public TLORangeEdge(ZoomableTimeline timeline, TimeLineObj tlo,
            TimelineRemembersPosition rp, boolean leftSide, Image image) {
        this.timeline = timeline;
        this.leftSide = leftSide;
        this.tlo = tlo;
        this.rp = rp;
        myLabel = image;

        myLabel.addMouseListener(this);

        myLabel.setStyleName("H-TimeBar-Edge");

        initWidget(myLabel);

        if (leftSide) {

            // tooltip = new
            // TooltipListener(format.format(tlo.getStartDate()));
        } else {

            // tooltip = new
            // TooltipListener(format.format(tlo.getEndDate()));
        }

        // myLabel.addMouseListener(tooltip);

    }

    public void onMouseDown(Widget sender, int x, int y) {
        resizing = true;
        DOM.setCapture(myLabel.getElement());
        resizeStartX = x;

    }

    public void onMouseUp(Widget sender, int x, int y) {

        if (resizing) {
            int clientX = DOM.eventGetClientX(DOM.eventGetCurrentEvent());
            timeline.setDateFromDrag(tlo, rp, clientX, leftSide, true);
        }

        resizing = false;
        DOM.releaseCapture(myLabel.getElement());
    }

    public void onMouseEnter(Widget sender) {
    }

    public void onMouseLeave(Widget sender) {
    }

    public void onMouseMove(Widget sender, int x, int y) {
        if (resizing) {

            int clientX = DOM.eventGetClientX(DOM.eventGetCurrentEvent());
            System.out.println("resiszeStart " + resizeStartX + " " + x
                    + " clientX " + clientX);

            Date rtn = timeline.setDateFromDrag(tlo, rp, clientX,
                    leftSide, false);

            // tooltip.setHTML(format.format(rtn));

        }
    }
}
