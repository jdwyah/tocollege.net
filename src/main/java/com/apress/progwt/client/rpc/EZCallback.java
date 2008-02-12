package com.apress.progwt.client.rpc;

import com.apress.progwt.client.util.Logger;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Intended for local callbacks.
 * 
 * Won't put anything in the status window.
 * 
 * @author Jeff Dwyer
 * 
 */
public abstract class EZCallback<T> implements AsyncCallback<T> {
    public void onFailure(Throwable caught) {
        Logger
                .log("EZCall failed! " + caught + " "
                        + caught.getMessage());
    }

}
