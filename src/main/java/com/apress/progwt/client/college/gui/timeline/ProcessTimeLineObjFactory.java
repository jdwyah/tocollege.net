package com.apress.progwt.client.college.gui.timeline;

import com.apress.progwt.client.college.gui.ProcessCheckboxWidget;
import com.apress.progwt.client.college.gui.ProcessPercentWidget;
import com.apress.progwt.client.consts.ConstHolder;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.gui.timeline.TLOWrapper;
import com.apress.progwt.client.gui.timeline.TimeLineObj;
import com.apress.progwt.client.gui.timeline.TimeLineObjFactory;
import com.apress.progwt.client.gui.timeline.TimelineRemembersPosition;
import com.apress.progwt.client.gui.timeline.ZoomableTimeline;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

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
            ZoomableTimeline<?> zoomableTimeline,
            TimelineController controller, TimeLineObj<?> tlo) {

        // if (tlo.getHasDate() instanceof HippoDate) {
        // TLORangeWidget tlow = new TLORangeWidget(zoomableTimeline,
        // tlo);
        // tlow.addMouseWheelListener(zoomableTimeline);
        // return tlow;
        // } else {
        //

        Widget mainWidget = null;
        Label mainLabel = null;
        TLOWrapper tlow = null;
        if (tlo.getHasDate() instanceof ProcessTimeLineEntry) {

            Image dragImage = ConstHolder.images.bullet_blue()
                    .createImage();

            ProcessTimeLineEntry pte = (ProcessTimeLineEntry) tlo
                    .getHasDate();
            ProcessType processType = pte.getProcessType();

            String imageName = processType.getImageName();
            if (imageName != null) {
                if (imageName.equals("applying")) {
                    mainWidget = ConstHolder.images.applying()
                            .createImage();
                } else if (imageName.equals("accepted")) {
                    mainWidget = ConstHolder.images.accepted()
                            .createImage();
                } else if (imageName.equals("rejected")) {
                    mainWidget = ConstHolder.images.rejected()
                            .createImage();
                } else if (imageName.equals("applied")) {
                    mainWidget = ConstHolder.images.applied()
                            .createImage();
                } else if (imageName.equals("considering")) {
                    mainWidget = ConstHolder.images.considering()
                            .createImage();
                }
            } else {
                if (processType.isPercentage()) {
                    mainWidget = new ProcessPercentWidget(controller,
                            processType, pte.getProcessValue());
                } else {
                    mainWidget = new ProcessCheckboxWidget(controller,
                            processType, pte.getProcessValue());
                }
                mainLabel = new Label(processType.getName(), false);
            }

            tlow = new TLOWrapper(zoomableTimeline, tlo, dragImage,
                    mainWidget, mainLabel);

        }

        tlow.addMouseWheelListener(zoomableTimeline);

        return tlow;
    }
}
