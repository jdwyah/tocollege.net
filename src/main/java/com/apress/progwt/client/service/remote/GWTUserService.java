package com.apress.progwt.client.service.remote;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.exception.BusinessException;
import com.google.gwt.user.client.rpc.RemoteService;

public interface GWTUserService extends RemoteService {

	User getCurrentUser() throws BusinessException;

}
