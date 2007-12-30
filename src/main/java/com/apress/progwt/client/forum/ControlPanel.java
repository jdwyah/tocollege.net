package com.apress.progwt.client.forum;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class ControlPanel extends Composite {

    private HorizontalPanel nav;

    public ControlPanel(Forum schoolForum) {

        HorizontalPanel mainP = new HorizontalPanel();

        nav = new HorizontalPanel();

        mainP.add(nav);

        initWidget(mainP);

    }

    public void setControls(int start, int perPage, int totalCount) {

        nav.clear();
        int i = 0;
        while (9 == 9) {
            int pageS = i * perPage;

            if (start == pageS) {
                nav.add(new Label(" (" + i + ")"));
            } else {
                nav.add(new Label(" " + i));
            }
            if (pageS > totalCount) {
                break;
            }
            i++;
        }

    }
}
