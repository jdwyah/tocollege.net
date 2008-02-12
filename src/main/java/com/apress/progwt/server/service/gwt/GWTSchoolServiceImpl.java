package com.apress.progwt.server.service.gwt;

import java.util.List;

import org.apache.log4j.Logger;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.exception.BusinessException;
import com.apress.progwt.client.service.remote.GWTSchoolService;
import com.apress.progwt.server.gwt.GWTSpringControllerReplacement;
import com.apress.progwt.server.service.SchoolService;

public class GWTSchoolServiceImpl extends GWTSpringControllerReplacement
        implements GWTSchoolService {
    private static final Logger log = Logger
            .getLogger(GWTSchoolServiceImpl.class);

    private SchoolService schoolService;

    public List<School> getSchoolsMatching(String match)
            throws BusinessException {
        return schoolService.getSchoolsMatching(match);
    }

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

}
