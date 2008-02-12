/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
