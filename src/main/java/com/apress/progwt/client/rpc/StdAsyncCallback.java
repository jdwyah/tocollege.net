package com.apress.progwt.client.rpc;

import com.apress.progwt.client.util.Logger;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Std Wrapper. Make sure to call super.onSuccess() so we can clean up the
 * status window
 * 
 * @author Jeff Dwyer
 * 
 */
public class StdAsyncCallback<T> implements AsyncCallback<T> {
    private String msg;

    public StdAsyncCallback(String msg) {
        this.msg = msg;
    }

    public void onFailure(Throwable caught) {
        Logger.log("Async " + msg + " failed! " + caught + " "
                + caught.getMessage());
    }

    public void onSuccess(T result) {
        Logger.log("Async Success: " + msg);
    }

}
