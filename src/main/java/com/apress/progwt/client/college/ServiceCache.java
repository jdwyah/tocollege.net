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
import com.apress.progwt.client.gears.ClientDB;
import com.apress.progwt.client.gears.GearsRowMapper;
import com.apress.progwt.client.gears.EmptyClientDB;
import com.apress.progwt.client.gears.SimpleGearsDatabase;
import com.apress.progwt.client.gears.StringMapper;
import com.apress.progwt.client.json.JSONSerializer;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.apress.progwt.client.service.remote.GWTSchoolServiceAsync;
import com.apress.progwt.client.service.remote.GWTUserServiceAsync;
import com.google.gwt.gears.core.client.Gears;
import com.google.gwt.gears.core.client.GearsException;
import com.google.gwt.gears.database.client.DatabaseException;
import com.google.gwt.gears.database.client.ResultSet;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ServiceCache {

    private static final String MATCH = "schoolmatch";
    private static final String PROCESSTYPE = "processTypeMatch";

    private GWTUserServiceAsync userService;
    private GWTSchoolServiceAsync schoolService;

    private ClientDB db;

    public ServiceCache(GWTApp gwtApp) {

        this.schoolService = gwtApp.getSchoolService();
        this.userService = gwtApp.getUserService();

        Log.info("Gears is installed: " + Gears.isInstalled());
        if (Gears.isInstalled()) {
            try {
                db = new SimpleGearsDatabase("tocollege.net");
                db.createKeyedStringStore(MATCH);
                db.createKeyedStringStore(PROCESSTYPE);

            } catch (GearsException e) {
                // Must handle gracefully. This error may be as simple as
                // user's not allowing gears to run for us.
                Log.warn("No Gears " + e.getMessage());

            }
        }
        if (db == null) {
            Log.info("Creating Empty Client DB");
            db = new EmptyClientDB();
        }

    }

    private String currentToken;

    private StringMapper stringMapper = new StringMapper();
    private GearsRowMapper<ProcessType> processTypeMapper = new GearsRowMapper<ProcessType>() {

        public ProcessType mapRow(ResultSet rs, int rowNum)
                throws DatabaseException {

            return JSONSerializer.deserialize(JSONParser.parse(rs
                    .getFieldAsString(0)), ProcessType.class);
        }
    };

    public void match(final String query,
            final AsyncCallback<List<String>> origCallback) {

        List<String> stored = db.getFromKeyedStringStore(MATCH, query,
                stringMapper);
        if (stored != null && !stored.isEmpty()) {
            Log.info("school HIT " + query);
            origCallback.onSuccess(stored);
            return;
        } else {
            Log.info("school MISS " + query);
            schoolService.getSchoolsMatching(query,
                    new AsyncCallback<List<String>>() {

                        public void onFailure(Throwable caught) {
                            origCallback.onFailure(caught);
                        }

                        public void onSuccess(List<String> result) {
                            origCallback.onSuccess(result);
                            for (String string : result) {
                                db.addToKeyedStringStore(MATCH, query,
                                        string);
                            }
                        }
                    });
        }
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

    public void matchProcessType(final String query,
            final AsyncCallback<List<ProcessType>> origCallback) {

        List<ProcessType> stored = db.getFromKeyedStringStore(MATCH,
                query, processTypeMapper);
        if (stored != null && !stored.isEmpty()) {
            origCallback.onSuccess(stored);
            return;
        } else {
            schoolService.matchProcessType(query,
                    new AsyncCallback<List<ProcessType>>() {

                        public void onFailure(Throwable caught) {
                            origCallback.onFailure(caught);
                        }

                        public void onSuccess(List<ProcessType> result) {
                            origCallback.onSuccess(result);
                            for (ProcessType pType : result) {

                                db.addToKeyedStringStore(MATCH, query,
                                        pType);
                            }
                        }
                    });
        }

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
