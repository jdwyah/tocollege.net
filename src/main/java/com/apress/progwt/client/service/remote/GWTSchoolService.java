package com.apress.progwt.client.service.remote;

import java.util.List;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.exception.BusinessException;
import com.google.gwt.user.client.rpc.RemoteService;

public interface GWTSchoolService extends RemoteService {

    List<School> getSchoolsMatching(String match)
            throws BusinessException;

}
