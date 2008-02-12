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
package com.apress.progwt.client.service.remote;

import java.util.List;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.domain.dto.ForumBootstrap;
import com.apress.progwt.client.domain.dto.PostsList;
import com.apress.progwt.client.domain.forum.ForumTopic;
import com.apress.progwt.client.exception.SiteException;
import com.google.gwt.user.client.rpc.RemoteService;

public interface GWTSchoolService extends RemoteService {

    SiteCommand executeAndSaveCommand(SiteCommand comm)
            throws SiteException;

    ForumBootstrap forumBootstrapDummy();

    List<School> getAllSchools() throws SiteException;

    PostsList getForum(ForumTopic forumTopic, int start, int max)
            throws SiteException;

    School getSchoolDetails(String schoolName);

    List<String> getSchoolsMatching(String match) throws SiteException;

    List<ProcessType> matchProcessType(String queryString)
            throws SiteException;
}
