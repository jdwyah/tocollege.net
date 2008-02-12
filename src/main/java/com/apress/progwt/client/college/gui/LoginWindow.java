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
package com.apress.progwt.client.college.gui;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.Interactive;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoginWindow extends DialogBox {

    private static final String SECURITY_URL = "j_acegi_security_check?gwt=true";

    private FormPanel form;
    private Label messageLabel;
    private LoginListener listener;

    private TextBox username;

    /**
     * this has the secureTargetURL which is a way for us to get to the
     * right page even after a reloaction to OpenID login.
     */
    private String secureTargetURL;

    private static boolean semaphore = false;

    private static String lastNameEntered = "";

    /**
     * Prevents multiple instances with a semaphore.
     * 
     * @param ConstHolder.myConstants -
     *            NOTE: this is used by both AddLink & Hippo modules, so
     *            we can't rely on Manager.ConstHolder.myConstants being
     *            initialized.
     * 
     * @param manager
     */
    public LoginWindow(LoginListener listener, String secureTargetURL) {
        super(false, false);
        this.secureTargetURL = secureTargetURL;

        this.listener = listener;

        if (semaphore == false) {
            Log.debug("CREATING LoginWindow");
            semaphore = true;
        } else {
            Log.debug("KILLING LoginWindow");

            hide();
            return;
        }
        setText("Please Login");

        setupForm();

        addPopupListener(new PopupListener() {

            public void onPopupClosed(PopupPanel sender,
                    boolean autoClosed) {
                semaphore = false;
            }
        });

        setWidget(form);

        setStyleName("TC-Popup");

    }

    private void setupForm() {
        form = new FormPanel();

        form.setAction(Interactive.getRelativeURL(SECURITY_URL));

        form.setMethod(FormPanel.METHOD_POST);

        // Create a panel to hold all of the form widgets.

        VerticalPanel panel = new VerticalPanel();

        TabPanel tabs = new TabPanel();
        tabs.add(getOpenIDTab(), "OpenID");
        tabs.add(getUPTab(), "Username/Password");
        tabs.selectTab(1);

        panel.add(tabs);

        messageLabel = new Label("");
        panel.add(messageLabel);

        form.addFormHandler(new FormHandler() {

            // note, this doesn't need to be perfectly secure. We just
            // want to know that we think we're secure. The next request
            // will tell us for sure
            public void onSubmitComplete(FormSubmitCompleteEvent event) {

                Log.debug("submit event results " + event.getResults());
                if (event.getResults().equals("OK")) {
                    success();
                } else {
                    Log.warn("Login Fail: " + event.getResults());
                    failure();
                }

                // // TODO parse bad password etc. Super-Fragile string
                // comps
                // if (event.getResults() == null
                // || -1 != event.getResults().indexOf(
                // "not successful")
                // || -1 != event.getResults().indexOf(
                // "Bad credentials")
                // || -1 != event.getResults().indexOf("404")) {
                // Log.warn("Login Fail: " + event.getResults());
                // failure();
                // } else {
                // Log.info("Login Success");
                // Log.debug(event.getResults());
                // success();
                // }

            }

            public void onSubmit(FormSubmitEvent event) {
                Log.debug("submit to " + form.getAction());

                // This event is fired just before the form is submitted.
                // We can take
                // this opportunity to perform validation.
                // if (username.getText().length() == 0) {
                // AlertDialog.alert("Username cannot be empty");
                // event.setCancelled(true);
                // }
                // if (password.getText().length() == 0) {
                // AlertDialog.alert("Password cannot be empty");
                // event.setCancelled(true);
                // }
                lastNameEntered = username.getText();
            }
        });

        form.setWidget(panel);

    }

    private Widget getUPTab() {
        VerticalPanel uptab = new VerticalPanel();
        username = new TextBox();
        username.setName("j_username");
        username.setText(lastNameEntered);

        KeyboardListener enterListener = new KeyboardListenerAdapter() {
            public void onKeyPress(Widget sender, char keyCode,
                    int modifiers) {
                if (keyCode == KEY_ENTER) {
                    form.submit();
                }
            }
        };

        final PasswordTextBox password = new PasswordTextBox();
        password.setName("j_password");
        password.addKeyboardListener(enterListener);

        username.setText("test");
        password.setText("testaroo");

        HorizontalPanel userP = new HorizontalPanel();

        userP.add(new Label("Username"));
        userP.add(username);

        HorizontalPanel passPanel = new HorizontalPanel();
        passPanel.add(new Label("Password"));
        passPanel.add(password);

        uptab.add(userP);
        uptab.add(passPanel);
        uptab.add(new Button("Login", new ClickListener() {
            public void onClick(Widget sender) {
                form.submit();
            }
        }));
        return uptab;
    }

    private Widget getOpenIDTab() {

        HorizontalPanel hP = new HorizontalPanel();

        hP
                .add(new ExternalLink("Do OpenID login", secureTargetURL,
                        true));

        return hP;
    }

    private void failure() {
        // messageLabel.setText(ConstHolder.myConstants.login_failure());
        messageLabel.setText("Failure");
    }

    private void success() {
        // messageLabel.setText(ConstHolder.myConstants.login_success());
        messageLabel.setText("Success");
        listener.loginSuccess();

        Timer t = new Timer() {
            public void run() {
                // free up the login lock for next time
                semaphore = false;
                hide();
            }
        };
        t.schedule(2000);

    }

}
