package com.apress.progwt.server.service.gwt;

import java.util.List;

import org.apache.log4j.Logger;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.domain.dto.ForumBootstrap;
import com.apress.progwt.client.domain.dto.PostsList;
import com.apress.progwt.client.exception.BusinessException;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.client.service.remote.GWTSchoolService;
import com.apress.progwt.server.gwt.GWTSpringControllerReplacement;
import com.apress.progwt.server.service.SchoolService;

/**
 * Simple wrapper of SchoolService so that SchoolService doesn't need to
 * extend GWTSpringControllerReplacement
 * 
 * @author Jeff Dwyer
 * 
 */
public class GWTSchoolServiceImpl extends GWTSpringControllerReplacement
        implements GWTSchoolService {

    private static final Logger log = Logger
            .getLogger(GWTSchoolServiceImpl.class);

    private SchoolService schoolService;

    public List<String> getSchoolsMatching(String match)
            throws BusinessException {
        return schoolService.getSchoolsMatching(match);
    }

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public SiteCommand executeAndSaveCommand(SiteCommand comm)
            throws SiteException {
        return schoolService.executeAndSaveCommand(comm);
    }

    public List<ProcessType> matchProcessType(String queryString)
            throws SiteException {
        return schoolService.matchProcessType(queryString);
    }

    public List<School> getAllSchools() throws SiteException {
        return schoolService.getAllSchools();
    }

    public PostsList getSchoolThreads(long schoolID, int start, int max)
            throws SiteException {
        return schoolService.getSchoolThreads(schoolID, start, max);
    }

    public School getSchoolDetails(String schoolName) {
        return schoolService.getSchoolDetails(schoolName);
    }

    public PostsList getPostsForThread(ForumPost post, int start, int max) {
        return schoolService.getPostsForThread(post, start, max);
    }

    public ForumBootstrap forumBootstrapDummy() {
        return null;
    }

}
