package com.apress.progwt.client.service.remote;

import java.util.List;

import com.apress.progwt.client.domain.School;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GWTSchoolServiceAsync {

    void getSchoolsMatching(String match,
            AsyncCallback<List<School>> callback);

}
