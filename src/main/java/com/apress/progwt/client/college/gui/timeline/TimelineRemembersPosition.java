package com.apress.progwt.client.college.gui.timeline;

import com.apress.progwt.client.college.gui.RemembersPosition;

public interface TimelineRemembersPosition extends RemembersPosition {
    int getWidth();

    TimeLineObj getTLO();

    void updateTitle();

    void addStyleName(String string);

    void removeStyleName(String string);
}
