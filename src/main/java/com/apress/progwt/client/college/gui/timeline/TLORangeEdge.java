package com.apress.progwt.client.college.gui.timeline;

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
