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

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.exception.SiteSecurityException;

public class SaveApplicationCommand extends AbstractCommand implements
        Serializable {

    private long applicationID;
    private Application original;

    private Application toSave;

    public SaveApplicationCommand() {
        super();
    }

    public SaveApplicationCommand(Application application) {
        super(application);
        applicationID = application.getId();
        original = application;
    }

    public boolean haveYouSecuredYourselfAndFilteredUserInput() {
        return true;
    }

    public void execute(CommandService commandService)
            throws SiteSecurityException {

        toSave = commandService.get(Application.class, applicationID);

        commandService.assertUserIsAuthenticated(toSave.getUser());

        Log.debug("SaveApplicationCommand " + applicationID + " loaded: "
                + toSave + " original: " + original);

        String xssFiltered = commandService.filterHTML(original
                .getNotes());

        escapeStringList(commandService, original.getCons());
        escapeStringList(commandService, original.getPros());

        toSave.setCons(original.getCons());
        toSave.setPros(original.getPros());
        toSave.setNotes(xssFiltered);

        Log.debug("notes " + toSave.getNotes() + " o: "
                + original.getNotes());

        commandService.save(toSave);

    }

    /**
     * For testing
     * 
     * @return
     */
    public Application getToSave() {
        return toSave;
    }

    @Override
    public String toString() {
        return "SaveApplicationCommand applicationID " + applicationID;
    }

}
