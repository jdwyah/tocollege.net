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
package com.apress.progwt.client.college.gui.ext;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ContextMenu extends PopupPanel {

    protected int x;
    protected int y;

    public ContextMenu(Widget w, final int x, final int y) {
        super(true);
        this.x = x;
        this.y = y;
        setPopupPosition(x, y);

        Log.debug("ContextMenu " + x + " " + y);

        add(w);

        setStylePrimaryName("ContextMenu");
    }

    public ContextMenu(int x, int y) {
        this(new SimplePanel(), x, y);
    }

    public void show(int x, int y) {
        setPopupPosition(x, y);
        super.show();
    }

}
