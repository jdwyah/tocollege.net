package com.apress.progwt.client.exception;

import java.io.Serializable;

public class BusinessException extends SiteException implements
        Serializable {

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Exception e) {
        super(e);
    }

}
