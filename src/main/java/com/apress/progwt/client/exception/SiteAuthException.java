package com.apress.progwt.client.exception;

import java.io.Serializable;

public class SiteAuthException extends SiteException implements
        Serializable {

    public SiteAuthException() {
    }

    public SiteAuthException(String message) {
        super(message);
    }

    public SiteAuthException(Exception e) {
        super(e);
    }

}
