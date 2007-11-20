package com.apress.progwt.client.domain.commands;

import com.apress.progwt.client.exception.SiteException;

public interface SiteCommand {
    void execute(CommandService commandService) throws SiteException;
}
