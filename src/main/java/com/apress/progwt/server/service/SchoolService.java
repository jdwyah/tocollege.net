package com.apress.progwt.server.service;

import java.util.List;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.commands.AbstractCommand;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.server.domain.SchoolPopularity;

public interface SchoolService {

    School getSchoolDetails(String schoolname);

    List<SchoolPopularity> getPopularSchools();

    List<School> getTopSchools();

    List<School> getSchoolsMatching(String match);

    void executeAndSaveCommand(AbstractCommand comm) throws SiteException;

    void executeAndSaveCommand(AbstractCommand comm, boolean useUserCache)
            throws SiteException;

    List<ProcessType> matchProcessType(String queryString);

}
