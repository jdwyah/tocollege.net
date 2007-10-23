package com.apress.progwt.client.calculator;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;

public abstract class CalcButton extends Button implements ClickListener {

    public CalcButton(String displayString) {
        super(displayString);
        addStyleName("CalcButton");
        addClickListener(this);
    }

    protected void explode() {
        GUIEffects.appear(this, 200);
    }
}
