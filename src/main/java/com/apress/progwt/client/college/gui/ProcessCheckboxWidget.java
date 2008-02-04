package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.college.gui.timeline.TimelineController;
import com.apress.progwt.client.consts.ConstHolder;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProcessCheckboxWidget extends ProcessValueEditWidget {

    private Image theImage;

    public ProcessCheckboxWidget(TimelineController controller,
            ProcessType processType, ProcessValue processValue) {
        super(controller, processType, processValue);

        theImage = ConstHolder.images.unchecked().createImage();
        getImage(value, theImage);

        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.add(theImage);

        theImage.addClickListener(this);

        initWidget(mainPanel);

    }

    private void getImage(ProcessValue value, Image theLabel2) {
        if (value.getPctComplete() < .5) {
            ConstHolder.images.unchecked().applyTo(theLabel2);
        } else {
            ConstHolder.images.checked().applyTo(theLabel2);
        }
    }

    public void onClick(Widget widg) {
        super.onClick(widg);
        getImage(value, theImage);
    }

}
