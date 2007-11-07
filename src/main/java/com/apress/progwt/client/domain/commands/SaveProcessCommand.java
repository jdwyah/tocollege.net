package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.SchoolAndAppProcess;
import com.apress.progwt.client.exception.SiteException;

public class SaveProcessCommand extends AbstractCommand implements
        Serializable {

    private ProcessValue value;
    private long processTypeID;
    private long schoolAppID;

    public SaveProcessCommand() {
    }

    public SaveProcessCommand(SchoolAndAppProcess application,
            ProcessType type, ProcessValue value) {
        this.value = value;
        this.schoolAppID = application.getId();
        this.processTypeID = type.getId();
    }

    @Override
    public void executeCommand(CommandService commandService)
            throws SiteException {
        commandService
                .saveProcessValue(schoolAppID, processTypeID, value);
    }
}
