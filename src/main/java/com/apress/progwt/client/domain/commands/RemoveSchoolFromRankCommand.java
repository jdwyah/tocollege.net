package com.apress.progwt.client.domain.commands;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.client.util.Utilities;

public class RemoveSchoolFromRankCommand extends AbstractCommand
        implements Serializable {

    private long userID;
    private long schoolID;

    public RemoveSchoolFromRankCommand() {

    }

    public RemoveSchoolFromRankCommand(School school, User user) {
        super(school, user);
        this.schoolID = school.getId();
        this.userID = user.getId();
    }

    public boolean haveYouSecuredYourselfAndFilteredUserInput() {
        return true;
    }

    public void execute(CommandService commandService)
            throws SiteException {

        User loadedUser = commandService.get(User.class, userID);
        School school = commandService.get(School.class, schoolID);

        commandService.assertUserIsAuthenticated(loadedUser);

        List<Application> rankings = loadedUser.getSchoolRankings();

        Application toDelete = null;
        for (Iterator<Application> iterator = rankings.iterator(); iterator
                .hasNext();) {
            Application scAndApp = (Application) iterator.next();
            if (scAndApp.getSchool().equals(school)) {
                toDelete = scAndApp;
                iterator.remove();
            }
        }

        if (toDelete == null) {
            throw new SiteException(
                    "Tried to delete non-existant application.");
        }

        commandService.delete(toDelete);

        // without this, we'll leave a gap in the sort order
        Utilities.reOrder(rankings);

        commandService.save(loadedUser);

    }

}
