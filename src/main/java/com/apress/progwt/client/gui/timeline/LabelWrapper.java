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
