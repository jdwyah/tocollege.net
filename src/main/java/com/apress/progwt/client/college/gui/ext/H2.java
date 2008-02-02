package com.apress.progwt.client.college.gui.ext;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class H2 extends Widget {
    public H2(String label) {
        setElement(DOM.createElement("h2"));
        DOM.setInnerText(getElement(), label);
    }
}
