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

    private void doLogin(String secureTargetURL,
            AsyncCallback<User> callback) {
        this.callback = callback;
        LoginWindow lw = new LoginWindow(this, secureTargetURL);
        lw.center();
    }

    public void loginSuccess() {
        serviceCache.getCurrentUser(callback);
    }

    /**
     * Callers need to specify a URL that we can use to perform possible
     * login via forwarding. We can't do OpenID logins with a simple
     * asynchronous form POST (maybe with Comet?) because the user might
     * need to go offsite to type in their URL. When they come back from
     * that, we need to have a URL that will bring us back to the desired
     * state of the application.
     * 
     * Of course for Username/Password logins, this URL will not be used,
     * we'll just do the asyncronous FormPost.
     * 
     * 
     * @param secureTargetURL
     * @param callback -
     *            A method that needs a user. We'll supply a user, or
     *            force a login if there is no current user.
     */
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
