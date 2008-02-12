package com.apress.progwt.client.exception;

import java.io.Serializable;

public class SiteException extends Exception implements Serializable {

    public SiteException() {
    }

    public SiteException(String message) {
        super(message);
    }

    public SiteException(Exception e) {
        super(e);
    }

}
