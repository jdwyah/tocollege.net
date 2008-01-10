package com.apress.progwt.client.service.remote;

import java.util.List;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.domain.dto.PostsList;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GWTSchoolServiceAsync {

    void getSchoolsMatching(String match,
            AsyncCallback<List<String>> callback);

    void executeAndSaveCommand(SiteCommand comm,
            AsyncCallback<SiteCommand> callback);

    void matchProcessType(String queryString,
            AsyncCallback<List<ProcessType>> callback);

    void getAllSchools(AsyncCallback<List<School>> asyncCallback);

    void getSchoolThreads(long schoolID, int start, int max,
            AsyncCallback<PostsList> AsyncCallback);

    void getSchoolDetails(String schoolName,
            AsyncCallback<School> callback);

}
