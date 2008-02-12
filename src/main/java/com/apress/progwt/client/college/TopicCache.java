package com.apress.progwt.client.college;

import java.util.List;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.service.remote.GWTSchoolServiceAsync;
import com.apress.progwt.client.service.remote.GWTUserServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TopicCache {

    private GWTUserServiceAsync userService;
    private GWTSchoolServiceAsync schoolService;

    public TopicCache(CollegeBoundApp collegeBoundApp) {
        this.schoolService = collegeBoundApp.getSchoolService();
        this.userService = collegeBoundApp.getUserService();
    }

    public void match(String query,
            AsyncCallback<List<School>> asyncCallback) {

        schoolService.getSchoolsMatching(query, asyncCallback);

        // School sc = new School();
        // sc.setName("Matched school");
        // List<School> rtn = new LinkedList<School>();
        // rtn.add(sc);
        // asyncCallback.onSuccess(rtn);
    }

}
