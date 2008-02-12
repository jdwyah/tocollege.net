/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

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

                try {
                    HibernateProxy hp = (HibernateProxy) instance;
                    LazyInitializer li = hp.getHibernateLazyInitializer();
                    log.warn("On The Fly initialization: "
                            + li.getEntityName());
                    return li.getImplementation();

                } catch (ClassCastException c) {
                    log.error("error casting to HibernateProxy "
                            + instance);
                    return null;
                }

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
