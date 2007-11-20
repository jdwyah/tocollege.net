package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.exception.SiteException;

public class SaveProcessCommand extends AbstractCommand implements
        Serializable {

    private ProcessValue value;

    private long processTypeID;
    private long schoolAppID;

    public SaveProcessCommand() {
    }

    public SaveProcessCommand(Application application, ProcessType type,
            ProcessValue value) {
        super(application, type);
        this.value = value;
        this.schoolAppID = application.getId();
        this.processTypeID = type.getId();
    }

    public void execute(CommandService commandService)
            throws SiteException {

        Application application = commandService.get(Application.class,
                schoolAppID);

        ProcessType type = (ProcessType) commandService.get(
                ProcessType.class, processTypeID);
        application.getProcess().put(type, value);
        commandService.save(application);

    }

}
