package com.apress.progwt.client.gui.timeline;

public interface TimeLineObjFactory {
    TimelineRemembersPosition getWidget(
            ZoomableTimeline<?> zoomableTimeline, TimeLineObj<?> tlo);
}
