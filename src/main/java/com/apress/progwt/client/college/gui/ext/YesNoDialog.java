package com.apress.progwt.client.college.gui.ext;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Simple Dialog extension for prompting a user with an Ok/Cancel
 * question.
 * 
 * @author Jeff Dwyer
 * 
 */
public class YesNoDialog extends DialogBox {
    public YesNoDialog(String caption, String question, Command onYes) {
        this(caption, question, onYes, new Command() {
            public void execute() {
            }
        }, "Cancel", "Ok");
    }

    public YesNoDialog(String caption, String question,
            final Command onYes, final Command onNo, String noText,
            String yesText) {
        super(false, true);
        setText(caption);
        VerticalPanel mainP = new VerticalPanel();
        mainP.add(new Label(question));
        HorizontalPanel buttonP = new HorizontalPanel();

        Button yesB = new Button(yesText);
        yesB.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                onYes.execute();
                hide();
            }
        });
        Button noB = new Button(noText);
        noB.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                onNo.execute();
                hide();
            }
        });
        buttonP.add(noB);
        buttonP.add(yesB);
        mainP.add(buttonP);
        setWidget(mainP);
    }
}
