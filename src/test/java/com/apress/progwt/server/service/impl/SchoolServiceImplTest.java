package com.apress.progwt.server.service.impl;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.commands.SaveSchoolRankCommand;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.UserService;

public class SchoolServiceImplTest extends ServiceTestWithTransaction {

    private SchoolService schoolService;
    private UserService userService;

    public void testSaveSchoolRanking() throws SiteException {

        School dart = schoolService.getSchoolsMatching("Dartmouth Col")
                .get(0);

        SaveSchoolRankCommand comm = new SaveSchoolRankCommand(dart, 0);

        schoolService.executeAndSaveCommand(comm);

    }

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
