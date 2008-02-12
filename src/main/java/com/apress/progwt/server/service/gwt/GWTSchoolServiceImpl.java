/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.apress.progwt.server.service.gwt;

import java.util.List;

import org.apache.log4j.Logger;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.domain.dto.ForumBootstrap;
import com.apress.progwt.client.domain.dto.PostsList;
import com.apress.progwt.client.domain.forum.ForumTopic;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.client.service.remote.GWTSchoolService;
import com.apress.progwt.server.gwt.GWTController;
import com.apress.progwt.server.service.SchoolService;

/**
 * Simple wrapper of SchoolService so that SchoolService doesn't need to
 * extend GWTSpringControllerReplacement
 * 
 * @author Jeff Dwyer
 * 
 */
public class GWTSchoolServiceImpl extends GWTController
        implements GWTSchoolService {

    private static final Logger log = Logger
            .getLogger(GWTSchoolServiceImpl.class);

    private SchoolService schoolService;

    public List<String> getSchoolsMatching(String match)
            throws SiteException {
        return schoolService.getSchoolStringsMatching(match);
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

    public School getSchoolDetails(String schoolName) {
        return schoolService.getSchoolDetails(schoolName);
    }

    public ForumBootstrap forumBootstrapDummy() {
        return null;
    }

    public PostsList getForum(ForumTopic forumTopic, int start, int max)
            throws SiteException {
        return schoolService.getForum(forumTopic, start, max);
    }

}
