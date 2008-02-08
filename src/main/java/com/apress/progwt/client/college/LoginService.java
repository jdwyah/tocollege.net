package com.apress.progwt.client.college;

import com.apress.progwt.client.college.gui.LoginListener;
import com.apress.progwt.client.college.gui.LoginWindow;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginService implements LoginListener {

    private ServiceCache serviceCache;
    private AsyncCallback<User> callback;

    public LoginService(ServiceCache serviceCache) {
        this.serviceCache = serviceCache;
    }

    public void getUserOrDoLogin(final AsyncCallback<User> callback) {
        getUserOrDoLogin(null, callback);
    }

    private void doLogin(String secureTargetURL,
            AsyncCallback<User> callback) {
        this.callback = callback;
        LoginWindow lw = new LoginWindow(this);
        lw.center();
    }

    public void loginSuccess() {
        serviceCache.getCurrentUser(callback);
    }

    public void getUserOrDoLogin(final String secureTargetURL,
            final AsyncCallback<User> callback) {
        this.callback = callback;

        serviceCache
                .getCurrentUser(new StdAsyncCallback<User>("Get User") {

                    public void onSuccess(User result) {
                        super.onSuccess(result);
                        if (result == null) {
                            doLogin(secureTargetURL, callback);
                        } else {
                            callback.onSuccess(result);
                        }
                    }

                    public void onFailure(Throwable caught) {
                        super.onFailure(caught);
                        doLogin(secureTargetURL, callback);
                    }
                });

    }

}
