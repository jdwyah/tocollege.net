package com.apress.progwt.client.service.remote;

import java.util.List;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.domain.dto.ForumBootstrap;
import com.apress.progwt.client.domain.dto.PostsList;
import com.apress.progwt.client.domain.forum.ForumTopic;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GWTSchoolServiceAsync {

    void executeAndSaveCommand(SiteCommand comm,
            AsyncCallback<SiteCommand> callback);

    void forumBootstrapDummy(AsyncCallback<ForumBootstrap> callback);

    void getAllSchools(AsyncCallback<List<School>> callback);

    void getForum(ForumTopic forumTopic, int start, int max,
            AsyncCallback<PostsList> callback);

    void getSchoolDetails(String schoolName,
            AsyncCallback<School> callback);

    void getSchoolsMatching(String match,
            AsyncCallback<List<String>> callback);

    void matchProcessType(String queryString,
            AsyncCallback<List<ProcessType>> callback);
}
