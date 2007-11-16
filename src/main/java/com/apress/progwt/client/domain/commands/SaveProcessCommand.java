package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.exception.SiteException;

public class SaveProcessCommand extends AbstractCommand implements
        Serializable {

    private ProcessValue value;
    private transient Application application;
    private transient ProcessType type;
    private long processTypeID;
    private long schoolAppID;

    public SaveProcessCommand() {
    }

    public SaveProcessCommand(Application application, ProcessType type,
            ProcessValue value) {
        this.value = value;
        this.application = application;
        this.type = type;
        this.schoolAppID = application.getId();
        this.processTypeID = type.getId();
    }

    @Override
    public void executeCommandServer(CommandService commandService)
            throws SiteException {
        commandService
                .saveProcessValue(schoolAppID, processTypeID, value);
    }

    @Override
    public void executeCommandClient() throws SiteException {
        application.getProcess().put(type, value);
    }
}
