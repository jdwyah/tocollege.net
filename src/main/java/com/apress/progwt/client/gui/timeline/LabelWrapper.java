package com.apress.progwt.client.gui.timeline;

import com.apress.progwt.client.college.gui.RemembersPosition;
import com.apress.progwt.client.college.gui.ext.JSUtil;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LabelWrapper extends Label implements RemembersPosition {

    protected int left;
    private int top;

    public LabelWrapper(String s, int left, int top) {
        super(s, false);
        this.left = left;
        this.top = top;
        JSUtil.disableSelect(getElement());
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public Widget getWidget() {

        return this;
    }

    public void zoomToScale(double currentScale) {
        // TODO Auto-generated method stub

    }

    public void setTop(int top) {
        this.top = top;
    }
}
