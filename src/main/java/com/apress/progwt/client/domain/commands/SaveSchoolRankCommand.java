package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.domain.School;

public class SaveSchoolRankCommand extends AbstractCommand implements
        Serializable {

    private int rank;

    public SaveSchoolRankCommand(School dart, int rank) {
        super(dart);
        this.rank = rank;
    }

    @Override
    public void executeCommand() {
        System.out.println("Execute Command" + getObjects().get(0));
    }

}
