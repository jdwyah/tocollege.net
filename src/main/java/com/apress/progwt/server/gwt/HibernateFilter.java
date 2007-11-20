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
import org.hibernate.proxy.pojo.cglib.CGLIBLazyInitializer;

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
            HashSet<Object> hashSet = new HashSet<Object>();
            PersistentSet persSet = (PersistentSet) instance;
            if (persSet.wasInitialized()) {
                hashSet.addAll(persSet);
            }
            return hashSet;
        }
        if (instance instanceof PersistentList) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            PersistentList persList = (PersistentList) instance;
            if (persList.wasInitialized()) {
                arrayList.addAll(persList);
            }
            return arrayList;
        }
        if (instance instanceof PersistentBag) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            PersistentBag persBag = (PersistentBag) instance;
            if (persBag.wasInitialized()) {
                arrayList.addAll(persBag);
            }
            return arrayList;
        }
        if (instance instanceof PersistentMap) {
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            PersistentMap persMap = (PersistentMap) instance;
            if (persMap.wasInitialized()) {
                hashMap.putAll(persMap);
            }
            return hashMap;
        }
        if (instance.getClass().getName().contains("CGLIB")) {

            if (Hibernate.isInitialized(instance)) {

                CGLIBLazyInitializer cg = (CGLIBLazyInitializer) instance;
                log.warn("On The Fly initialization: "
                        + cg.getEntityName());
                return cg.getImplementation();

                // Hibernate.initialize(instance);
                //
                //               
                // log.warn("\nentity: " + cg.getEntityName()
                // + "\nidentifier" + cg.getIdentifier()
                // + "\nimplemenation " + cg.getImplementation());
                //
                // log.warn("On The Fly initialization: " + instance
                // + " now: " + instance.getClass().getName());
                //
                // if (instance instanceof ReallyCloneable) {
                // log.debug(instance.getClass().getName()
                // + " CGLIB Cloning " + instance);
                // return ((ReallyCloneable) instance).clone();
                // } else {
                // log
                // .warn("Initialized, but doesn't implement
                // ReallyCloneable"
                // + instance.getClass()
                // + " "
                // + instance.getClass().getName());
                // throw new CouldntFixCGLIBException(
                // instance.getClass()
                // + " must implement ReallyCloneable if we're to fix
                // it.");
                // }
            } else {
                log.debug("Uninitialized CGLIB");
                return null;
            }
        }

        return instance;
    }

}
