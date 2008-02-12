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
package com.apress.progwt.server.dao;

import java.util.List;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.Loadable;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.RatingType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.dto.PostsList;

public interface SchoolDAO {

    void delete(Loadable loadable);

    School getSchoolFromName(String name);

    List<School> getAllSchools(int start, int max);

    List<School> getSchoolsMatching(String match);

    List<School> getSchoolsMatching(String match, int start, int max);

    Loadable get(Class<? extends Loadable> loadable, Long id);

    Loadable save(Loadable loadable);

    List<ProcessType> matchProcessType(String queryString);

    ProcessType getProcessForName(String string);

    List<ProcessType> getDefaultProcessTypes();

    List<RatingType> getDefaultRatingTypes();

    PostsList getThreads(Class<? extends ForumPost> forumClass,
            String topicName, long topicID, int start, int max);

    PostsList getSchoolThreads(long schoolID, int start, int max);

    PostsList getUserThreads(long userID, int start, int max);

    PostsList getPostsForThread(ForumPost post, int start, int max);

    PostsList getRecentForumPosts(int start, int max);

    void incrementSchoolPopularity(School school);

    List<User> getUsersInterestedIn(School school);

}
