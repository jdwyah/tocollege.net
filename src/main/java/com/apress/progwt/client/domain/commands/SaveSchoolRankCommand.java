package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.exception.SiteException;

public class SaveSchoolRankCommand extends AbstractCommand implements
        Serializable {

    private int rank;
    private School school;

    public SaveSchoolRankCommand() {
        super();
    }

    public SaveSchoolRankCommand(School school, int rank) {
        this.school = school;
        this.rank = rank;
    }

    @Override
    public void executeCommandServer(CommandService commandService) {
        System.out.println("Execute Command");

        commandService.setSchoolAtRank(school, rank);

    }

    @Override
    public void executeCommandClient() throws SiteException {
        // TODO Auto-generated method stub

    }

}
