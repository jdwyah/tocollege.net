package com.apress.progwt.client.exception;

import java.io.Serializable;

/**
 * serialize message ourselves, because Exception is not Serializable and
 * thus breaks the GWT serializable chain.
 * 
 * @author Jeff Dwyer
 * 
 */
public class SiteException extends Exception implements Serializable {

    private String message;

    public SiteException() {

    }

    public SiteException(String message) {
        super(message);
        this.message = message;
    }

    public SiteException(Exception e) {
        super(e);
        this.message = e.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }

}
