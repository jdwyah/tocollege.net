package com.apress.progwt.client.college;

import java.util.List;

import com.apress.progwt.client.college.gui.CollegeEntry;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.commands.AbstractCommand;
import com.apress.progwt.client.domain.commands.SaveSchoolRankCommand;
import com.apress.progwt.client.rpc.EZCallback;
import com.apress.progwt.client.service.remote.GWTSchoolServiceAsync;
import com.apress.progwt.client.service.remote.GWTUserServiceAsync;
import com.apress.progwt.client.util.Logger;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ServiceCache {

    private GWTUserServiceAsync userService;
    private GWTSchoolServiceAsync schoolService;

    public ServiceCache(CollegeBoundApp collegeBoundApp) {
        this.schoolService = collegeBoundApp.getSchoolService();
        this.userService = collegeBoundApp.getUserService();
    }

    public void match(String query,
            AsyncCallback<List<School>> asyncCallback) {

        schoolService.getSchoolsMatching(query, asyncCallback);

        // School sc = new School();
        // sc.setName("Matched school");
        // List<School> rtn = new ArrayList<School>();
        // rtn.add(sc);
        // asyncCallback.onSuccess(rtn);
    }

    public void saveEntry(CollegeEntry entry, int index) {

        System.out.println("SAVING ENTRY!!!!!!!!!");

        final SaveSchoolRankCommand comm = new SaveSchoolRankCommand(
                entry.getSchoolAndApplication().getSchool(), index);
        schoolService.executeAndSaveCommand(comm,
                new EZCallback<Boolean>() {
                    public void onSuccess(Boolean success) {
                        Logger.debug("Success");

                        // comm.setCurrentUser()
                        // comm.executeCommand();
                    }
                });

    }

    public void getCurrentUser(AsyncCallback<User> callback) {
        userService.getCurrentUser(callback);
    }

    public void matchProcessType(String queryString,
            AsyncCallback<List<ProcessType>> callback) {

        schoolService.matchProcessType(queryString, callback);
    }

    public void executeCommand(AbstractCommand command,
            AsyncCallback<Boolean> callback) {
        schoolService.executeAndSaveCommand(command, callback);
    }

}
