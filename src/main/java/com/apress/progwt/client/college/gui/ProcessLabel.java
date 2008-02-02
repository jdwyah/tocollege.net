package com.apress.progwt.client.college.gui;

import java.util.Date;

import com.apress.progwt.client.college.gui.timeline.TimelineController;
import com.apress.progwt.client.domain.ProcessType;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ProcessLabel extends Label {

    private ProcessType processType;

    public ProcessLabel(final ProcessType processType,
            final TimelineController controller, final Date date) {
        super(processType.getName());

        this.processType = processType;
        setStylePrimaryName("ProcessLabel");
        addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                controller.addProcess(processType, date);
            }
        });
    }

    public ProcessType getProcessType() {
        return processType;
    }

}
