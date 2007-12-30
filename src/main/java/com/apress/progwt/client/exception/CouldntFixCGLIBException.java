package com.apress.progwt.client.exception;

import java.io.Serializable;

public class CouldntFixCGLIBException extends RuntimeException implements
        Serializable {

    public CouldntFixCGLIBException() {
    }

    public CouldntFixCGLIBException(String string) {
        super(string);
    }
}
