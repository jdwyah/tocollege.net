package com.apress.progwt.client.college.gui.status;

import java.util.HashMap;
import java.util.Map;

import com.apress.progwt.client.calculator.GUIEffects;
import com.apress.progwt.client.util.Logger;
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
 * .StatusLabels { width: 5px; height: 20px; filter: alpha(opacity = 40);
 * -moz-opacity: .40; opacity: .40; -khtml-opacity: .4; }
 * 
 * .StatusLabels .Send { background: yellow; }
 * 
 * .StatusLabels .Fail { background: red; }
 * 
 * .StatusLabels .Success { background-color: green; }
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

        displayPanel.setStylePrimaryName("StatusLabels");

        add(displayPanel);

        setStylePrimaryName("StatusPanel");
    }

    public void update(int id, String string, StatusCode statusCode) {

        Logger.debug("UPDATE " + id + " " + string + " "
                + statusCode.getCode());

        if (statusCode == StatusCode.SEND) {
            StatusLabel lab = new StatusLabel(string, statusCode);

            map.put(new Integer(id), lab);
            displayPanel.add(lab);
        } else if (statusCode == StatusCode.SUCCESS) {
            final StatusLabel sl = map.get(new Integer(id));
            if (sl != null) {
                sl.setCode(statusCode);
                GUIEffects.fadeAndRemove(sl, 2000);
            }
        }
        // FAIL
        else {
            StatusLabel sl = map.get(new Integer(id));
            if (sl != null) {
                sl.setText(string);
                sl.setCode(statusCode);
                GUIEffects.fadeAndRemove(sl, 3000, 6000);
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

        public StatusLabel(String string, StatusCode statusCode) {
            super(" ");
            this.string = string;
            setCode(statusCode);
            addMouseListener(this);
        }

        private void setCode(StatusCode statusCode) {
            setStylePrimaryName(statusCode.getCode());
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
