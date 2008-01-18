package com.apress.progwt.client.exception;

import java.io.Serializable;

public class SiteSecurityException extends BusinessException implements
        Serializable {

    public SiteSecurityException() {
    }

    public SiteSecurityException(String message) {
        super(message);
    }

    public SiteSecurityException(Exception e) {
        super(e);
    }

}
