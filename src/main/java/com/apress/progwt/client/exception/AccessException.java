package com.apress.progwt.client.exception;

import java.io.Serializable;

public class AccessException extends BusinessException implements
        Serializable {

    public AccessException() {
    }

    public AccessException(String message) {
        super(message);
    }

    public AccessException(Exception e) {
        super(e);
    }

}
