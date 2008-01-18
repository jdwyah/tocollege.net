package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.domain.Application;

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

    public void execute(CommandService commandService) {

        toSave = commandService.get(Application.class, applicationID);

        System.out.println(applicationID + " loaded: " + toSave
                + " original: " + original);

        String xssFiltered = commandService.filterHTML(original
                .getNotes());

        sanitizeStringList(commandService, original.getCons());
        sanitizeStringList(commandService, original.getPros());

        toSave.setCons(original.getCons());
        toSave.setPros(original.getPros());
        toSave.setNotes(xssFiltered);

        System.out.println("notes " + toSave.getNotes() + " o: "
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
