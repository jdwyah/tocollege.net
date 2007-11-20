package com.apress.progwt.client.service.remote;

import java.util.List;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GWTSchoolServiceAsync {

    void getSchoolsMatching(String match,
            AsyncCallback<List<School>> callback);

    void executeAndSaveCommand(SiteCommand comm,
            AsyncCallback<SiteCommand> callback);

    void matchProcessType(String queryString,
            AsyncCallback<List<ProcessType>> callback);

    void getAllSchools(AsyncCallback<List<School>> asyncCallback);

}
