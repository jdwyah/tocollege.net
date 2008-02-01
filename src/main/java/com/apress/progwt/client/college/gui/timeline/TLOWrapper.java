package com.apress.progwt.client.college.gui.timeline;

import com.apress.progwt.client.college.gui.ext.FocusPanelExt;
import com.apress.progwt.client.college.gui.ext.JSUtil;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseWheelListener;
import com.google.gwt.user.client.ui.SourcesMouseWheelEvents;
import com.google.gwt.user.client.ui.Widget;

public class TLOWrapper<T> extends FocusPanelExt implements
        TimelineRemembersPosition, SourcesMouseWheelEvents {

    private TimeLineObj<T> tlo;
    private int top;
    private Label label;

    private Image image;

    public TLOWrapper(ZoomableTimeline<T> timeline,
            final TimeLineObj<T> tlo, Image image) {
        this.tlo = tlo;
        this.top = 0;
        this.image = image;

        HorizontalPanel panel = new HorizontalPanel();

        label = new Label(tlo.getHasDate().getTitle(), false);

        TLORangeEdge edge = new TLORangeEdge(timeline, tlo, this, true,
                image);

        panel.add(edge);
        panel.add(label);

        addClickListener(timeline);
        addDblClickListener(timeline);

        setWidget(panel);

        label.setStyleName("H-TLOWrapper");

        JSUtil.disableSelect(getElement());
    }

    public int getLeft() {
        return tlo.getLeft();
    }

    public int getTop() {
        return top;
    }

    public Widget getWidget() {

        return this;
    }

    public void addMouseWheelListener(MouseWheelListener listener) {
        label.addMouseWheelListener(listener);
        image.addMouseWheelListener(listener);
    }

    public void removeMouseWheelListener(MouseWheelListener listener) {
        image.removeMouseWheelListener(listener);
        label.removeMouseWheelListener(listener);
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void zoomToScale(double currentScale) {

    }

    public TimeLineObj<T> getTLO() {
        return tlo;
    }

    /**
     * PEND MED weak 11 * #letters = width assumption
     */
    public int getWidth() {
        return 11 * tlo.getObject().toString().length();
    }

    public String toString() {
        return "TLOWrapper " + tlo.getObject().toString();
    }

    public void updateTitle() {
        label.setText(tlo.getHasDate().getTitle());
    }

}
