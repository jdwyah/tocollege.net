package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.exception.SiteException;

public abstract class AbstractCommand implements Serializable {

    public AbstractCommand() {
    }

    public abstract void executeCommandServer(CommandService commandService)
            throws SiteException;

    public abstract void executeCommandClient() throws SiteException;

}
