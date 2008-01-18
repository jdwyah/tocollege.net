package com.apress.progwt.client.service.remote;

import com.apress.progwt.client.domain.dto.UserAndToken;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GWTUserServiceAsync {

    void getCurrentUser(AsyncCallback<UserAndToken> callback);

}
