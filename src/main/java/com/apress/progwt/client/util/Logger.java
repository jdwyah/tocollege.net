package com.apress.progwt.client.util;

public class Logger {

	private static boolean debug = false;

	public static void log(String msg) {
		System.out.println("Warn: " + msg);
		logN(msg);
	}

	public static void error(String msg) {
		System.out.println("Error: " + msg);
		logN(msg);
	}

	private static native void logN(String msg) /*-{
	if(window.console) {
	        window.console.log(msg);
	}
	}-*/;

	public static void debug(String string) {
		if (debug) {
			log(string);
		}
	}
}
