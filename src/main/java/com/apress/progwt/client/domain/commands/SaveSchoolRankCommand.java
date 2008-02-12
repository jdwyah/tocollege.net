/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.apress.progwt.client.domain.commands;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.util.Utilities;

public class SaveSchoolRankCommand extends AbstractCommand implements
        Serializable {

    private int rank;
    private long schoolID;
    private long userID;

    private long savedApplicationID;

    public SaveSchoolRankCommand() {
        super();
    }

    public SaveSchoolRankCommand(School school, User user, int rank) {
        super(school, user);
        this.schoolID = school.getId();
        this.rank = rank;
        this.userID = user.getId();
    }

    public boolean haveYouSecuredYourselfAndFilteredUserInput() {
        return true;
    }

    public void execute(CommandService commandService) {
        // Log.debug("Execute Command");

        User currentUser = commandService.get(User.class, userID);

        assertUserIsAuthenticated(currentUser);

        List<Application> rankings = currentUser.getSchoolRankings();

        // Utilities.reOrder(rankings, currentUser, rank)

        Application sap = null;

        School school = commandService.get(School.class, schoolID);

        for (Iterator iterator = rankings.iterator(); iterator.hasNext();) {
            Application scAndApp = (Application) iterator.next();

            if (scAndApp.getSchool().equals(school)) {
                sap = scAndApp;
            }
        }

        if (null == sap) {
            // Log.debug("\n----B-CREATE-NEW-SAP----\n");
            sap = new Application(school);
            commandService.save(sap);
            currentUser.addRanked(rank, sap);
        }

        // use this to set the orderProperty, since the list
        // implementation didn't work
        Utilities.reOrder(rankings, sap, rank);

        // Log.debug("Command Executed");
        // Log.debug("User " + currentUser);

        for (Application ranked : currentUser.getSchoolRankings()) {
            Log.debug("Command.Ranks To Save: Rank: " + ranked);
        }
        commandService.save(currentUser);

        this.savedApplicationID = sap.getId();
        // commandService.setSchoolAtRank(school, rank);

    }

    public long getSavedApplicationID() {
        return savedApplicationID;
    }

}
