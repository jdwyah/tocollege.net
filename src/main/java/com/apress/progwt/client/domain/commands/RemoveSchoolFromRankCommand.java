package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.exception.SiteException;

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
    public void executeCommandServer(CommandService commandService) {

        commandService.removeSchool(school);

    }

    @Override
    public void executeCommandClient() throws SiteException {
        // TODO Auto-generated method stub

    }

}
