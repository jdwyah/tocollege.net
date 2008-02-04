package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.college.gui.timeline.TimelineController;
import com.apress.progwt.client.consts.ConstHolder;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProcessPercentWidget extends ProcessValueEditWidget {

    private Image theImage;

    public ProcessPercentWidget(TimelineController controller,
            ProcessType processType, ProcessValue processValue) {
        super(controller, processType, processValue);

        theImage = ConstHolder.images.pctStatus0().createImage();
        getImage(processValue, theImage);

        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.add(theImage);

        theImage.addClickListener(this);

        initWidget(mainPanel);

    }

    private void getImage(ProcessValue value, Image theLabel2) {
        if (value.getPctComplete() < .25) {
            ConstHolder.images.pctStatus0().applyTo(theLabel2);
        } else if (value.getPctComplete() < .5) {
            ConstHolder.images.pctStatus25().applyTo(theLabel2);
        } else if (value.getPctComplete() < .75) {
            ConstHolder.images.pctStatus50().applyTo(theLabel2);
        } else if (value.getPctComplete() < 1) {
            ConstHolder.images.pctStatus75().applyTo(theLabel2);
        } else {
            ConstHolder.images.pctStatus100().applyTo(theLabel2);
        }

    }

    public void onClick(Widget widg) {
        super.onClick(widg);
        getImage(value, theImage);
    }

}
