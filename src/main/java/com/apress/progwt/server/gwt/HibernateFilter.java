package com.apress.progwt.server.gwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.collection.PersistentBag;
import org.hibernate.collection.PersistentList;
import org.hibernate.collection.PersistentMap;
import org.hibernate.collection.PersistentSet;

import com.apress.progwt.client.domain.ReallyCloneable;
import com.apress.progwt.client.exception.CouldntFixCGLIBException;

public class HibernateFilter {
    private static final Logger log = Logger
            .getLogger(HibernateFilter.class);

    public static Object filter(Object instance) {
        if (instance == null) {
            return instance;
        }
        if (instance instanceof Date) {
            return new java.util.Date(((java.util.Date) instance)
                    .getTime());
        }

        if (instance instanceof PersistentSet) {
            HashSet hashSet = new HashSet();
            PersistentSet persSet = (PersistentSet) instance;
            if (persSet.wasInitialized()) {
                hashSet.addAll(persSet);
            }
            return hashSet;
        }
        if (instance instanceof PersistentList) {
            ArrayList arrayList = new ArrayList();
            PersistentList persList = (PersistentList) instance;
            if (persList.wasInitialized()) {
                arrayList.addAll(persList);
            }
            return arrayList;
        }
        if (instance instanceof PersistentBag) {
            ArrayList arrayList = new ArrayList();
            PersistentBag persBag = (PersistentBag) instance;
            if (persBag.wasInitialized()) {
                arrayList.addAll(persBag);
            }
            return arrayList;
        }
        if (instance instanceof PersistentMap) {
            HashMap hashMap = new HashMap();
            PersistentMap persMap = (PersistentMap) instance;
            if (persMap.wasInitialized()) {
                hashMap.putAll(persMap);
            }
            return hashMap;
        }
        if (instance.getClass().getName().contains("CGLIB")) {

            if (Hibernate.isInitialized(instance)) {
                if (instance instanceof ReallyCloneable) {
                    log.debug(instance.getClass().getName()
                            + " CGLIB Cloning " + instance);
                    return ((ReallyCloneable) instance).clone();
                } else {
                    log
                            .warn("Uninitialized but doesn't implement ReallyCloneable"
                                    + instance.getClass());
                    throw new CouldntFixCGLIBException(
                            instance.getClass()
                                    + " must implement ReallyCloneable if we're to fix it.");
                }
            } else {
                log.debug("Uninitialized CGLIB");
                return null;
            }
        }

        return instance;
    }

}
