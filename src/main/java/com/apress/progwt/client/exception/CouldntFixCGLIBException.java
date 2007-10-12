package com.apress.progwt.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CouldntFixCGLIBException extends RuntimeException implements IsSerializable {
	
	public CouldntFixCGLIBException(){}
	
	public CouldntFixCGLIBException(String string) {
		super(string);
	}
}

