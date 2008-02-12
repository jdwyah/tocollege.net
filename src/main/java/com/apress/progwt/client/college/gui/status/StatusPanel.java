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
package com.apress.progwt.client.college.gui.status;

import java.util.HashMap;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.calculator.GUIEffects;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel to display asynchronous status messages.
 * 
 * Style suggestions:
 * 
 * .StatusLabel { width: 5px; height: 20px; filter: alpha(opacity = 40);
 * -moz-opacity: .40; opacity: .40; -khtml-opacity: .4; }
 * 
 * .StatusLabel-Send { background: yellow; }
 * 
 * .StatusLabel-Fail { background: red; }
 * 
 * .StatusLabel-Success { background-color: green; }
 * 
 * @author Jeff Dwyer
 * 
 */
public class StatusPanel extends SimplePanel {

    public enum StatusCode {
        SEND("Send"), SUCCESS("Success"), FAIL("Fail");
        private final String code;

        private StatusCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

    }

    private Map<Integer, StatusLabel> map = new HashMap<Integer, StatusLabel>();

    private CellPanel displayPanel = new HorizontalPanel();

    public StatusPanel() {

        add(displayPanel);

        setStylePrimaryName("StatusPanel");
    }

    public void update(int id, String string, StatusCode statusCode) {

        Log.debug("StatusPanel.UPDATE " + id + " " + string + " "
                + statusCode.getCode());

        if (statusCode == StatusCode.SEND) {
            StatusLabel lab = new StatusLabel(string, statusCode);

            map.put(new Integer(id), lab);
            displayPanel.add(lab);
        } else if (statusCode == StatusCode.SUCCESS) {
            final StatusLabel sl = map.remove(new Integer(id));
            if (sl != null) {
                sl.setCode(statusCode);
                GUIEffects.fadeAndRemove(sl, 5000);
            }
        }
        // FAIL
        else {
            StatusLabel sl = map.remove(new Integer(id));
            if (sl != null) {
                sl.setText(string);
                sl.setCode(statusCode);
                GUIEffects.fadeAndRemove(sl, 6000, 8000);
            }
        }
    }

    /**
     * Individual Label. Mouse over to show contents.
     * 
     * Note: you can show the message if you want by changing the ctor.
     * 
     * @author Jeff Dwyer
     * 
     */
    private class StatusLabel extends Label implements MouseListener {
        private String string;
        private StatusCode currentCode;

        public StatusLabel(String string, StatusCode statusCode) {
            super(" ");
            this.string = string;
            setCode(statusCode);
            setStylePrimaryName("StatusLabel");

            addMouseListener(this);
        }

        private void setCode(StatusCode statusCode) {
            if (currentCode != null) {
                removeStyleDependentName(currentCode.getCode());
            }
            this.currentCode = statusCode;
            addStyleDependentName(currentCode.getCode());
        }

        public void onMouseEnter(Widget sender) {
            setText(string);
        }

        public void onMouseLeave(Widget sender) {
            setText("");
        }

        public void onMouseDown(Widget sender, int x, int y) {
        }

        public void onMouseMove(Widget sender, int x, int y) {
        }

        public void onMouseUp(Widget sender, int x, int y) {
        }
    }

}
