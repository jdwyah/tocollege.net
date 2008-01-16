package com.apress.progwt.client.domain.commands;

import java.io.Serializable;

import com.apress.progwt.client.domain.Application;

public class SaveApplicationCommand extends AbstractCommand implements
        Serializable {

    private long applicationID;
    private Application original;

    public SaveApplicationCommand() {
        super();
    }

    public SaveApplicationCommand(Application application) {
        super(application);
        applicationID = application.getId();
        original = application;
    }

    public void execute(CommandService commandService) {

        Application loaded = commandService.get(Application.class,
                applicationID);

        System.out.println(applicationID + " loaded: " + loaded
                + " original: " + original);

        loaded.setCons(original.getCons());
        loaded.setPros(original.getPros());
        loaded.setNotes(original.getNotes());

        System.out.println("notes " + loaded.getNotes() + " o: "
                + original.getNotes());

        commandService.save(loaded);

    }

    @Override
    public String toString() {
        return "SaveApplicationCommand applicationID " + applicationID;
    }

}
