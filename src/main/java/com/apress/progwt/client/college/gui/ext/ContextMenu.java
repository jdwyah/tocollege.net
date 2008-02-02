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
