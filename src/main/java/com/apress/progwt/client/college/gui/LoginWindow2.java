package com.apress.progwt.client.college.gui;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
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

public class LoginWindow2 extends DialogBox {

    private static String lastNameEntered = "";
    private static boolean semaphore = false;

    private LoginListener listener;
    private Label messageLabel;
    private PasswordTextBox password;
    private String secureTargetURL;

    private ServiceCache serviceCache;

    private TextBox username;

    /**
     * Prevents multiple instances with a semaphore.
     * 
     * @param manager
     */
    public LoginWindow2(LoginListener listener, String secureTargetURL,
            ServiceCache serviceCache) {
        super(false, false);

        this.secureTargetURL = secureTargetURL;
        this.listener = listener;
        this.serviceCache = serviceCache;

        if (semaphore == false) {
            Log.debug("CREATING LoginWindow");
            semaphore = true;
        } else {
            Log.debug("KILLING LoginWindow");

            hide();
            return;
        }
        setText("Please Login");

        Widget w = setupForm();

        addPopupListener(new PopupListener() {

            public void onPopupClosed(PopupPanel sender,
                    boolean autoClosed) {
                semaphore = false;
            }
        });

        setWidget(w);

        setStyleName("TC-Popup");

    }

    private void doUPLogin() {
        serviceCache.login(username.getText(), password.getText(),
                new StdAsyncCallback<User>("Login...") {

                    @Override
                    public void onFailure(Throwable caught) {
                        super.onFailure(caught);
                        failure();
                    }

                    @Override
                    public void onSuccess(User result) {
                        super.onSuccess(result);
                        success();
                    }

                });
    }

    private void failure() {
        // messageLabel.setText(ConstHolder.myConstants.login_failure());
        messageLabel.setText("Failure");
    }

    private Widget getOpenIDTab() {

        HorizontalPanel hP = new HorizontalPanel();

        hP
                .add(new ExternalLink("Do OpenID login", secureTargetURL,
                        true));

        return hP;
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
                    doUPLogin();
                }
            }
        };

        password = new PasswordTextBox();
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
                doUPLogin();
            }
        }));

        return uptab;
    }

    private Widget setupForm() {

        // Create a panel to hold all of the form widgets.

        VerticalPanel panel = new VerticalPanel();

        TabPanel tabs = new TabPanel();
        tabs.add(getOpenIDTab(), "OpenID");
        tabs.add(getUPTab(), "Username/Password");
        tabs.selectTab(1);

        panel.add(tabs);

        messageLabel = new Label("");
        panel.add(messageLabel);

        return panel;

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
