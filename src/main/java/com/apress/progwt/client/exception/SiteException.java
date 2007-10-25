package com.apress.progwt.client.exception;

public class SiteException extends Exception {

	public SiteException(String message) {
		super(message);
	}

	public SiteException(Exception e) {
		super(e);
	}

}
