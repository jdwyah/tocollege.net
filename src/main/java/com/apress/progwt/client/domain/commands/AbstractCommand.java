package com.apress.progwt.client.domain.commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.apress.progwt.client.domain.Loadable;

public abstract class AbstractCommand implements Serializable {

    private List<Class<Loadable>> classes = new ArrayList<Class<Loadable>>();
    private List<Long> ids = new ArrayList<Long>();
    private transient List<Loadable> objects = new ArrayList<Loadable>();

    public AbstractCommand(Loadable loadable) {
        classes.add((Class<Loadable>) loadable.getClass());
        ids.add(loadable.getId());

        objects.add(loadable);
    }

    public List<Loadable> getObjects() {
        return objects;
    }

    public List<Class<Loadable>> getClasses() {
        return classes;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void clearObject() {
        objects.clear();
    }

    public void addObject(Loadable l) {
        objects.add(l);
    }

    public abstract void executeCommand();

    public Set<Loadable> getDeleteSet() {
        return new HashSet<Loadable>();
    }
}
