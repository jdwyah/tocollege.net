package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.domain.ApplicationCheckbox;
import com.apress.progwt.client.domain.ApplicationCheckboxValue;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AppCheckboxWidget extends Label implements ClickListener {

    private ApplicationCheckbox appCheckType;
    private ApplicationCheckboxValue value;

    public AppCheckboxWidget(ApplicationCheckbox appCheckType,
            ApplicationCheckboxValue value) {
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
