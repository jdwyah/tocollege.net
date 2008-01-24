package com.apress.progwt.server.dao;

import java.util.List;

import com.apress.progwt.client.domain.Foo;
import com.apress.progwt.client.domain.Loadable;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.RatingType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.dto.PostsList;
import com.apress.progwt.client.domain.forum.ForumPost;

public interface SchoolDAO {

    void delete(Loadable loadable);

    School getSchoolFromName(String name);

    List<School> getAllSchools(int start, int max);

    List<School> getSchoolsMatching(String match);

    Loadable get(Class<? extends Loadable> loadable, Long id);

    Loadable save(Loadable loadable);

    Foo saveF();

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

}
