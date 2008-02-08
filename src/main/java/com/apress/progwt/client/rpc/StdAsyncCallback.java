package com.apress.progwt.client.rpc;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.college.gui.ext.AlertDialog;
import com.apress.progwt.client.college.gui.status.StatusPanel;
import com.apress.progwt.client.college.gui.status.StatusPanel.StatusCode;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.client.exception.SiteSecurityException;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Std Wrapper.
 * 
 * Use this when you'd liek to visualize the progress of your asynchronous
 * call.
 * 
 * Make sure to call super.onSuccess() when you override so we can clean
 * up the status window.
 * 
 * NOTE: the StatusPanel is set statically as a convenience. You should do
 * this while your application is bootstrapping.
 * 
 * @author Jeff Dwyer
 * 
 */
public class StdAsyncCallback<T> implements AsyncCallback<T> {

    private static StatusPanel manager;

    public static void setManager(StatusPanel manager) {
        StdAsyncCallback.manager = manager;
    }

    private static int callNum = 0;

    private String call;
    private int myNum;

    public StdAsyncCallback(String call) {
        this.call = call;
        myNum = callNum++;

        if (manager != null)
            manager.update(myNum, call, StatusCode.SEND);
    }

    public void onFailure(Throwable caught) {

        Log.warn(call + " failed! " + caught + " " + caught.getMessage());

        // StackTraceElement[] str = caught.getStackTrace();
        // Logger.log("stack trace size " + str.length);
        // for (int i = 0; i < str.length; i++) {
        // Logger.log(str[i].toString());
        // }

        if (caught.getMessage() != null) {
            if (caught.getMessage().startsWith("Username not found")) {
                AlertDialog.alert("Need Login " + caught);
            }
        }
        if (caught instanceof SiteSecurityException) {
            AlertDialog.alert("Access Exception " + caught);
        }

        if (manager != null) {
            try {
                SiteException hbe = (SiteException) caught;

                manager.update(myNum, call + " fail " + hbe.getMessage(),
                        StatusCode.FAIL);

            } catch (Exception e) {
                manager.update(myNum, call, StatusCode.FAIL);
            }
        }
    }

    public void onSuccess(T result) {
        if (manager != null) {
            manager.update(myNum, call, StatusCode.SUCCESS);
        }
    }

    public void setCall(String call) {
        this.call = call;
    }

}
