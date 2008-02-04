package com.apress.progwt.client.gui.timeline;

import com.apress.progwt.client.college.gui.timeline.TimelineController;

public interface TimeLineObjFactory {
    TimelineRemembersPosition getWidget(
            ZoomableTimeline<?> zoomableTimeline,
            TimelineController controller, TimeLineObj<?> tlo);
}
