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
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoginWindow extends DialogBox implements TabListener {

    private static final String SECURITY_URL = "j_acegi_security_check";
    private static final String SECURITY_URL_OPENID = "site/j_acegi_openid_start";

    private FormPanel form;
    private Label messageLabel;
    private LoginListener listener;

    private boolean isOpenID;

    private TextBox username;
    private TextBox openID;

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
    public LoginWindow(LoginListener listener) {
        super(false, false);

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

        setToOpenID(false);

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
        tabs.addTabListener(this);
        tabs.selectTab(1);

        panel.add(tabs);

        panel.add(new Button("Login", new ClickListener() {
            public void onClick(Widget sender) {
                form.submit();
            }
        }));

        messageLabel = new Label("");
        panel.add(messageLabel);

        form.addFormHandler(new FormHandler() {

            public void onSubmitComplete(FormSubmitCompleteEvent event) {

                // TODO parse bad password etc. Super-Fragile string comps
                if (event.getResults() == null
                        || -1 != event.getResults().indexOf(
                                "not successful")
                        || -1 != event.getResults().indexOf(
                                "Bad credentials")
                        || -1 != event.getResults().indexOf("404")) {
                    Log.warn("Login Fail: " + event.getResults());
                    failure();
                } else {
                    Log.info("Login Success");
                    Log.debug(event.getResults());
                    success();
                }

            }

            public void onSubmit(FormSubmitEvent event) {
                Log.debug("submit to " + form.getAction());

                // This event is fired just before the form is submitted.
                // We can take
                // this opportunity to perform validation.
                // if (username.getText().length() == 0) {
                // Window.alert("Username cannot be empty");
                // event.setCancelled(true);
                // }
                // if (password.getText().length() == 0) {
                // Window.alert("Password cannot be empty");
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

        return uptab;
    }

    private Widget getOpenIDTab() {

        openID = new TextBox();
        openID.setName("openid_url");
        openID.setText(lastNameEntered);

        HorizontalPanel hP = new HorizontalPanel();

        hP.add(new Label("Username"));
        hP.add(openID);

        return hP;
    }

    private void setToOpenID(boolean toOpenID) {
        if (toOpenID) {
            form.setAction(Interactive
                    .getRelativeURL(SECURITY_URL_OPENID));
        } else {
            form.setAction(Interactive.getRelativeURL(SECURITY_URL));
        }

        isOpenID = toOpenID;
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

    public boolean onBeforeTabSelected(SourcesTabEvents sender,
            int tabIndex) {
        return true;
    }

    public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
        if (tabIndex == 0) {
            setToOpenID(true);
        } else {
            setToOpenID(false);
        }
    }

}
