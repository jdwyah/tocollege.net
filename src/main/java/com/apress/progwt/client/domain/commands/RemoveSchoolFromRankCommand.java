package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.domain.School;

public class RemoveSchoolFromRankCommand extends AbstractCommand
        implements Serializable {

    private School school;

    public RemoveSchoolFromRankCommand() {
        super();
    }

    public RemoveSchoolFromRankCommand(School school) {
        this.school = school;
    }

    @Override
    public void executeCommand(CommandService commandService) {

        commandService.removeSchool(school);

    }

}
