package com.apress.progwt.client.domain.commands;

import com.apress.progwt.client.domain.Loadable;

public class MockCommandService implements CommandService {

    private AbstractCommand command;

    public MockCommandService(AbstractCommand command) {
        this.command = command;
    }

    public <T> T get(Class<T> clazz, long id) {
        return command.get(clazz, id);
    }

    public void save(Loadable o) {
        command.save(o);
    }
}
