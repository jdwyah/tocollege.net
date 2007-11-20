package com.apress.progwt.client.domain.commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.apress.progwt.client.domain.Loadable;

public abstract class AbstractCommand implements Serializable,
        SiteCommand, CommandService {

    private transient List<Object> objects = new ArrayList<Object>();

    public AbstractCommand(Object... arguments) {
        for (Object o : arguments) {
            objects.add(arguments);
        }
    }

    public <T> T get(Class<T> clazz, long id) {
        for (Object o : objects) {
            if (o.getClass() == clazz) {
                Loadable l = (Loadable) o;
                if (l.getId() == id) {
                    return (T) o;
                }
            }
        }
        return null;
    }

    public void save(Loadable o) {
        // Do Nothing
    }

}
