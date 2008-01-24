package com.apress.progwt.client.college;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.GWTApp;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.commands.AbstractCommand;
import com.apress.progwt.client.domain.commands.SiteCommand;
import com.apress.progwt.client.domain.dto.PostsList;
import com.apress.progwt.client.domain.dto.UserAndToken;
import com.apress.progwt.client.domain.forum.ForumTopic;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.client.rpc.StdAsyncCallback;
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

    /**
     * if we haven't got the token yet, go get one
     * 
     * @param command
     * @param callback
     */
    public void executeCommand(final AbstractCommand command,
            final AsyncCallback<SiteCommand> callback) {

        if (currentToken == null) {
            Log.info("Token was null, fetching.");
            getCurrentUser(new StdAsyncCallback<User>("Fetch Token") {
                public void onSuccess(User result) {
                    super.onSuccess(result);
                    Log.info("Retrying");
                    executeCommand(command, callback);
                }
            });

        } else {
            Log.info("Using token:" + currentToken);
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
    }

    public void getSchoolDetails(String replacementString,
            AsyncCallback<School> callback) {
        schoolService.getSchoolDetails(replacementString, callback);
    }

    public void getForum(ForumTopic forumTopic, int start, int max,
            AsyncCallback<PostsList> asyncCallback) {
        schoolService.getForum(forumTopic, start, max, asyncCallback);
    }
}
