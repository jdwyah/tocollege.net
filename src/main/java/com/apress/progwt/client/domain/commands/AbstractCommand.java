package com.apress.progwt.client.domain.commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.apress.progwt.client.domain.Loadable;

/**
 * Store
 * 
 * @author Jeff Dwyer
 * 
 */
public abstract class AbstractCommand implements Serializable,
        SiteCommand, CommandService {

    private transient List<Object> objects = new ArrayList<Object>();

    public AbstractCommand() {

    }

    public AbstractCommand(Object... arguments) {
        for (Object o : arguments) {
            objects.add(o);
        }
    }

    public <T> T get(Class<T> clazz, long id) {
        System.out.println("Load " + clazz + " " + id);
        for (Object o : objects) {
            if (o != null) {
                System.out.println("O: " + o + " " + o.getClass());
            } else {
                System.out.println("O: null");
            }
            if (o != null && o.getClass() == clazz) {

                Loadable l = (Loadable) o;
                System.out.println("l: " + l + " " + l.getId());
                if (l.getId() == id) {
                    return (T) o;
                }
            }
        }
        return null;
    }

    public void save(Loadable o) {
        // do nothing
    }

    public void delete(Loadable loadable) {
        loadable = null;
    }

}
