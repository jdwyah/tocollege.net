package com.apress.progwt.client.service.remote;

import java.util.List;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.domain.dto.SchoolThreads;
import com.apress.progwt.client.exception.BusinessException;
import com.apress.progwt.client.exception.SiteException;
import com.google.gwt.user.client.rpc.RemoteService;

public interface GWTSchoolService extends RemoteService {

    List<String> getSchoolsMatching(String match)
            throws BusinessException;

    SiteCommand executeAndSaveCommand(SiteCommand comm)
            throws SiteException;

    List<ProcessType> matchProcessType(String queryString)
            throws SiteException;

    List<School> getAllSchools() throws SiteException;

    SchoolThreads getThreads(long schoolID, int start, int max)
            throws SiteException;

    School getSchoolDetails(String schoolName);
}
