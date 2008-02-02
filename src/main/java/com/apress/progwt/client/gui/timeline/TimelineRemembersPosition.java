package com.apress.progwt.client.gui.timeline;

import com.apress.progwt.client.college.gui.RemembersPosition;

public interface TimelineRemembersPosition extends RemembersPosition {
    int getWidth();

    TimeLineObj getTLO();

    void updateTitle();

    void addStyleDependentName(String string);

    void removeStyleDependentName(String string);
}
