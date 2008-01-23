package com.apress.progwt.client.rpc;

import com.allen_sauer.gwt.log.client.Log;
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
        Log.warn("EZCall failed! " + caught + " " + caught.getMessage());
    }

}
