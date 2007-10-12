package com.apress.progwt.server.service;

import com.apress.progwt.client.exception.BusinessException;

public class PermissionDeniedException extends BusinessException {
	public PermissionDeniedException(String message) {
		super(message);
	}
}
