package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.college.gui.timeline.TimelineController;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class ProcessValueEditWidget extends Composite implements
        ClickListener {

    protected ProcessType processType;
    protected ProcessValue value;
    protected TimelineController controller;

    public ProcessValueEditWidget(TimelineController controller,
            ProcessType processType, ProcessValue value) {
        this.processType = processType;
        this.value = value;
        this.controller = controller;

    }

    public void onClick(Widget widg) {
        value.increment(processType);
        controller.saveProcess(processType, value);
    }
}
