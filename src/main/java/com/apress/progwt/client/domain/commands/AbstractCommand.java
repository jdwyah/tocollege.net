package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

public abstract class AbstractCommand implements Serializable {

    public AbstractCommand() {
    }

    public abstract void executeCommand(CommandService commandService);
}
