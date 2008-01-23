package com.apress.progwt.client.domain.commands;

import org.apache.commons.lang.StringEscapeUtils;

import com.apress.progwt.client.domain.Loadable;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.util.HTMLInputFilter;

public class MockCommandService implements CommandService {

    private AbstractCommand command;
    private static final HTMLInputFilter htmlFilter = new HTMLInputFilter();

    public MockCommandService(AbstractCommand command) {
        this.command = command;
    }

    public <T> T get(Class<T> clazz, long id) {
        return command.get(clazz, id);
    }

    public void save(Loadable o) {
        command.save(o);
    }

    public void delete(Loadable loadable) {
        command.delete(loadable);
    }

    public void assertUserIsAuthenticated(User loadedUser)
            throws SecurityException {
        command.assertUserIsAuthenticated(loadedUser);
    }

    public String filterHTML(String input) {
        return htmlFilter.filter(input);
    }

    public String escapeHtml(String string) {
        return StringEscapeUtils.escapeHtml(string);
    }
}
