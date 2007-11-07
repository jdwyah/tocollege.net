package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.exception.SiteException;

public abstract class AbstractCommand implements Serializable {

    public AbstractCommand() {
    }

    public abstract void executeCommand(CommandService commandService)
            throws SiteException;
}
