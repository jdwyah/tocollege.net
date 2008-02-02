package com.apress.progwt.client.college.gui.ext;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class H1 extends Widget {
    public H1(String label) {
        setElement(DOM.createElement("h1"));
        DOM.setInnerText(getElement(), label);
    }
}
