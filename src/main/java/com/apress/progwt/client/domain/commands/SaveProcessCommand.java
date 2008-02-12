/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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

    public boolean haveYouSecuredYourselfAndFilteredUserInput() {
        return true;
    }

    public void execute(CommandService commandService)
            throws SiteException {

        Application application = commandService.get(Application.class,
                schoolAppID);

        ProcessType type = (ProcessType) commandService.get(
                ProcessType.class, processTypeID);

        assertUserIsAuthenticated(application.getUser());

        application.getProcess().put(type, value);
        commandService.save(application);

    }

}
