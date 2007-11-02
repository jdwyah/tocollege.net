package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AppCheckboxWidget extends Label implements ClickListener {

    private ProcessType appCheckType;
    private ProcessValue value;

    public AppCheckboxWidget(ProcessType appCheckType, ProcessValue value) {
        this.appCheckType = appCheckType;
        this.value = value;

        setText(value.getString());

        addClickListener(this);
        setStyleName("TC-App-CheckBox");
    }

    public void onClick(Widget widg) {
        value.increment();
        setText(value.getString());
    }

}
