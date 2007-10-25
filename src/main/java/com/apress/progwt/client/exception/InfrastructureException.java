package com.apress.progwt.client.exception;

public class InfrastructureException extends SiteException {

    public InfrastructureException(String message) {
        super(message);
    }

    public InfrastructureException(Exception e) {
        super(e);
    }

}
