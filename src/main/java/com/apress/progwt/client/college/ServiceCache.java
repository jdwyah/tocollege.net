package com.apress.progwt.client.college;

import java.util.List;

import com.apress.progwt.client.GWTApp;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.commands.AbstractCommand;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.domain.dto.UserAndToken;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.client.service.remote.GWTSchoolServiceAsync;
import com.apress.progwt.client.service.remote.GWTUserServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ServiceCache {

    private GWTUserServiceAsync userService;
    private GWTSchoolServiceAsync schoolService;

    public ServiceCache(GWTApp gwtApp) {
        this.schoolService = gwtApp.getSchoolService();
        this.userService = gwtApp.getUserService();
    }

    private String currentToken;

    public void match(String query,
            AsyncCallback<List<String>> asyncCallback) {

        schoolService.getSchoolsMatching(query, asyncCallback);

        // School sc = new School();
        // sc.setName("Matched school");
        // List<School> rtn = new ArrayList<School>();
        // rtn.add(sc);
        // asyncCallback.onSuccess(rtn);
    }

    // public void saveEntry(CollegeEntry entry, User thisUser, int index)
    // {
    //
    // System.out.println("SAVING ENTRY!!!!!!!!!");
    //
    // final SaveSchoolRankCommand comm = new SaveSchoolRankCommand(
    // entry.getSchoolAndApplication().getSchool(), thisUser,
    // index);
    // executeCommand(comm, new EZCallback<SiteCommand>() {
    // public void onSuccess(SiteCommand success) {
    // Logger.debug("Success");
    //
    // // comm.setCurrentUser()
    // // comm.executeCommand();
    // }
    // });
    //
    // }

    public void getCurrentUser(final AsyncCallback<User> callback) {

        userService.getCurrentUser(new AsyncCallback<UserAndToken>() {

            public void onFailure(Throwable caught) {
                callback.onFailure(caught);
            }

            public void onSuccess(UserAndToken result) {
                currentToken = result.getToken();
                callback.onSuccess(result.getUser());
            }
        });
    }

    public void matchProcessType(String queryString,
            AsyncCallback<List<ProcessType>> callback) {

        schoolService.matchProcessType(queryString, callback);
    }

    public void executeCommand(final AbstractCommand command,
            final AsyncCallback<SiteCommand> callback) {

        command.setToken(currentToken);

        schoolService.executeAndSaveCommand(command,
                new AsyncCallback<SiteCommand>() {

                    public void onSuccess(SiteCommand result) {
                        try {
                            command.execute(command);
                        } catch (SiteException e) {
                            callback.onFailure(e);
                        }
                        callback.onSuccess(result);
                    }

                    public void onFailure(Throwable caught) {
                        callback.onFailure(caught);
                    }
                });

    }

    public void getSchoolDetails(String replacementString,
            AsyncCallback<School> callback) {
        schoolService.getSchoolDetails(replacementString, callback);
    }
}
