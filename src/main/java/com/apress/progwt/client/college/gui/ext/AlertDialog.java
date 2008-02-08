package com.apress.progwt.client.college.gui.ext;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Simple Alert
 * 
 * @author Jeff Dwyer
 * 
 */
public class AlertDialog extends DialogBox {
    public AlertDialog(String message) {
        this("Alert", message);
    }

    public AlertDialog(String caption, String message) {
        super(false, true);
        setText(caption);

        VerticalPanel mainP = new VerticalPanel();
        mainP.add(new Label(message));
        HorizontalPanel buttonP = new HorizontalPanel();

        Button okB = new Button("Ok");
        okB.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                hide();
            }
        });

        buttonP.add(okB);
        mainP.add(buttonP);
        setWidget(mainP);
    }

    public static void alert(String message) {
        AlertDialog ad = new AlertDialog(message);
        ad.center();
    }
}
