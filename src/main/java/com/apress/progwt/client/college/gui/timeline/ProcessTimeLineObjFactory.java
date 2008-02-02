package com.apress.progwt.client.college.gui.timeline;

import com.apress.progwt.client.consts.ConstHolder;
import com.apress.progwt.client.gui.timeline.TLOWrapper;
import com.apress.progwt.client.gui.timeline.TimeLineObj;
import com.apress.progwt.client.gui.timeline.TimeLineObjFactory;
import com.apress.progwt.client.gui.timeline.TimelineRemembersPosition;
import com.apress.progwt.client.gui.timeline.ZoomableTimeline;
import com.google.gwt.user.client.ui.Image;

public class ProcessTimeLineObjFactory implements TimeLineObjFactory {

    /**
     * mea culpa. putting all the instanceof stuff in one place. Is there
     * a better way to do this when I can't put anything to do with
     * ConstHolder.images into the real domain objects without fear of
     * them exploding in a non GWT context?
     * 
     * 
     * @param zoomableTimeline
     * @param manager
     * @param tlo
     * @return
     */
    public TimelineRemembersPosition getWidget(
            ZoomableTimeline<?> zoomableTimeline, TimeLineObj<?> tlo) {

        // if (tlo.getHasDate() instanceof HippoDate) {
        // TLORangeWidget tlow = new TLORangeWidget(zoomableTimeline,
        // tlo);
        // tlow.addMouseWheelListener(zoomableTimeline);
        // return tlow;
        // } else {
        //
        // Image image = null;
        // if (tlo.getHasDate() instanceof WebLink) {
        // image = ConstHolder.images.gadgetLinksTimeline()
        // .createImage();
        // } else if (tlo.getHasDate() instanceof Entry) {
        // image = ConstHolder.images.entryZoomBackTimeline()
        // .createImage();
        // } else if (tlo.getHasDate() instanceof GDocument) {
        // image = ConstHolder.images.gDocumentTimeline()
        // .createImage();
        // } else if (tlo.getHasDate() instanceof GSpreadsheet) {
        // image = ConstHolder.images.gSpreadsheetTimeline()
        // .createImage();
        // } else {
        // image = ConstHolder.images.bullet_blue().createImage();
        // }
        //
        // TLOWrapper tlow = new TLOWrapper(manager, zoomableTimeline,
        // tlo, image);
        // tlow.addMouseWheelListener(zoomableTimeline);
        // return tlow;
        // }

        Image image = ConstHolder.images.bullet_blue().createImage();
        return new TLOWrapper(zoomableTimeline, tlo, image);
    }
}
