package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.domain.School;

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
    public void executeCommand(CommandService commandService) {
        System.out.println("Execute Command");

        commandService.setSchoolAtRank(school, rank);

    }

}
