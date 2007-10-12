package com.apress.progwt.client.exception;


import com.apress.progwt.client.util.Logger;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;

public class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {

	public void onUncaughtException(Throwable e) {
		e.printStackTrace();
		Logger.error("Uncaught Exception " + e.getMessage());
	}

}
