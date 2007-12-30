package com.apress.progwt.server.service;

import java.util.List;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.domain.dto.SchoolThreads;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.server.domain.SchoolPopularity;

public interface SchoolService {

    School getSchoolDetails(String schoolname);

    List<SchoolPopularity> getPopularSchools();

    List<School> getTopSchools();

    List<School> getSchoolsMatching(String match);

    SiteCommand executeAndSaveCommand(SiteCommand comm)
            throws SiteException;

    SiteCommand executeAndSaveCommand(SiteCommand comm,
            boolean useUserCache) throws SiteException;

    List<ProcessType> matchProcessType(String queryString);

    List<School> getAllSchools();

    SchoolThreads getThreads(long schoolID, int start, int max);

}
