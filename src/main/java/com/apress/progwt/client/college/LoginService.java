package com.apress.progwt.client.college;

import com.apress.progwt.client.college.gui.LoginListener;
import com.apress.progwt.client.college.gui.LoginWindow;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginService implements LoginListener {

    private ServiceCache serviceCache;
    private AsyncCallback<User> callback;

    public LoginService(ServiceCache serviceCache) {
        this.serviceCache = serviceCache;
    }

    public void getUserOrDoLogin(final AsyncCallback<User> callback) {

        serviceCache.getCurrentUser(new AsyncCallback<User>() {

            public void onSuccess(User result) {
                if (result == null) {
                    doLogin(callback);
                } else {
                    callback.onSuccess(result);
                }
            }

            public void onFailure(Throwable caught) {
                doLogin(callback);
            }
        });

    }

    private void doLogin(AsyncCallback<User> callback) {
        this.callback = callback;
        LoginWindow lw = new LoginWindow(this);
        lw.center();
    }

    public void loginSuccess() {
        serviceCache.getCurrentUser(callback);
    }

}
