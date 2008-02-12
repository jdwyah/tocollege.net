package com.apress.progwt.client.exception;

import java.io.Serializable;

public class InfrastructureException extends SiteException implements
        Serializable {

    public InfrastructureException() {
    }

    public InfrastructureException(String message) {
        super(message);
    }

    public InfrastructureException(Exception e) {
        super(e);
    }

}
