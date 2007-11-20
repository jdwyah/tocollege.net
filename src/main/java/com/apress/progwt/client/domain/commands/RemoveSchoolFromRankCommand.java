package com.apress.progwt.client.domain.commands;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;

public class RemoveSchoolFromRankCommand extends AbstractCommand
        implements Serializable {

    private long userID;
    private long schoolID;

    public RemoveSchoolFromRankCommand() {
        super();
    }

    public RemoveSchoolFromRankCommand(School school, User user) {
        super(school, user);
        this.schoolID = school.getId();
        this.userID = user.getId();
    }

    public void execute(CommandService commandService) {

        User currentUser = commandService.get(User.class, userID);
        School school = commandService.get(School.class, schoolID);
        List<Application> rankings = currentUser.getSchoolRankings();

        for (Iterator iterator = rankings.iterator(); iterator.hasNext();) {
            Application scAndApp = (Application) iterator.next();
            if (scAndApp.getSchool().equals(school)) {
                iterator.remove();
            }
        }
        commandService.save(currentUser);

    }

}
