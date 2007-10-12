package com.apress.progwt.client.service.remote;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GWTUserServiceAsync {

	void getCurrentUser(AsyncCallback callback);
	
}
