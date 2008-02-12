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
