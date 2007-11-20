package com.apress.progwt.client.domain.commands;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.util.Utilities;

public class SaveSchoolRankCommand extends AbstractCommand implements
        Serializable {

    private int rank;
    private long schoolID;
    private long userID;

    public SaveSchoolRankCommand() {
        super();
    }

    public SaveSchoolRankCommand(School school, User user, int rank) {
        super(school, user);
        this.schoolID = school.getId();
        this.rank = rank;
        this.userID = user.getId();
    }

    public void execute(CommandService commandService) {
        System.out.println("Execute Command");

        System.out.println("\n----A----\n");
        User currentUser = commandService.get(User.class, userID);

        List<Application> rankings = currentUser.getSchoolRankings();

        System.out.println("\n----A2----\n");

        // Utilities.reOrder(rankings, currentUser, rank)

        Application sap = null;

        School school = commandService.get(School.class, schoolID);

        for (Iterator iterator = rankings.iterator(); iterator.hasNext();) {
            Application scAndApp = (Application) iterator.next();

            System.out.println("\n----A3-LOOP----\n");
            if (scAndApp.getSchool().equals(school)) {
                sap = scAndApp;
            }
        }
        System.out.println("\n----B----\n");

        if (null == sap) {
            System.out.println("\n----B-CREATE-NEW-SAP----\n");
            sap = new Application(school);
            commandService.save(sap);
            currentUser.addRanked(rank, sap);
        }

        // use this to set the orderProperty, since the list
        // implementation didn't work
        Utilities.reOrder(rankings, sap, rank);

        System.out.println("\n----C----\n");

        System.out.println("Command Executed");
        System.out.println("User " + currentUser);

        for (Application ranked : currentUser.getSchoolRankings()) {
            System.out.println("Command.Ranks To Save: Rank: " + ranked);
        }
        commandService.save(currentUser);

        // commandService.setSchoolAtRank(school, rank);

    }

}
