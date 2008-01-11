package com.apress.progwt.client.service.remote;

import java.util.List;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.domain.dto.PostsList;
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

    PostsList getSchoolThreads(long schoolID, int start, int max)
            throws SiteException;

    PostsList getPostsForThread(ForumPost post, int start, int max);

    School getSchoolDetails(String schoolName);
}
