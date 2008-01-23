package com.apress.progwt.client.domain.commands;

import com.apress.progwt.client.domain.Loadable;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.exception.SiteSecurityException;

public interface CommandService {

    <T> T get(Class<T> clazz, long id);

    void delete(Loadable loadable);

    void save(Loadable o);

    void assertUserIsAuthenticated(User toCheck)
            throws SiteSecurityException;

    String filterHTML(String input);

    String escapeHtml(String string);
}
