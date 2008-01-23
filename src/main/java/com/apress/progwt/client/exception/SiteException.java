package com.apress.progwt.client.exception;

import java.io.Serializable;

/**
 * Serialize message ourselves, because Exception is not Serializable and
 * thus breaks the GWT serializable chain, the super(message) will not be
 * serialized. This means we need to override getMessage() as well and
 * we'll need to be careful about using other Exception methods as well.
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

    /**
     * Overriden to return our individually serialized value since
     * Exception base class is not serializable.
     */
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }

}
