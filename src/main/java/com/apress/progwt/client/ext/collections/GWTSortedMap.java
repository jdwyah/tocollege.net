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
package com.apress.progwt.client.ext.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;

/**
 * Implement a SortedMap for GWT using existing collection classes.
 * 
 * @author Jeff Dwyer
 * 
 * @param <X>
 * @param <V>
 */
public class GWTSortedMap<X extends Comparable<? super X>, V> implements
        Map<X, V> {

    private ListImplementingASet<X> keys = new ListImplementingASet<X>();
    private Map<X, V> map = new HashMap<X, V>();

    private boolean dirty = false;

    private Comparator<? super X> compare = null;

    /**
     * use default comparator
     * 
     */
    public GWTSortedMap() {

    }

    /**
     * specify key comparator
     * 
     * @param comp
     */
    public GWTSortedMap(Comparator<? super X> comp) {
        this.compare = comp;
    }

    public List<X> getKeyList() {
        keySet();
        return keys.getList();
    }

    /**
     * Carefull. This class manages this itself, but if you want to
     * prevent a sort you can...
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void clear() {
        keys.clear();
        map.clear();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public int size() {
        return map.size();
    }

    public V put(X key, V value) {
        dirty = true;
        keys.add(key);
        return map.put(key, value);
    }

    public void putAll(Map<? extends X, ? extends V> t) {
        dirty = true;
        keys.addAll(t.keySet());
        map.putAll(t);
    }

    public boolean containsKey(Object key) {
        return keys.contains(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    /**
     * Unimplemented. Call keySet() instead
     */
    public Set<java.util.Map.Entry<X, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    public V get(Object key) {
        return map.get(key);
    }

    public Set<X> keySet() {
        if (dirty) {
            Log.debug("GWTSortedMap.SORT ");
            if (compare == null) {
                Collections.sort(keys.getList());
            } else {
                Collections.sort(keys.getList(), compare);
            }
            dirty = false;
        }
        return keys;
    }

    public V remove(Object key) {
        keys.remove(key);
        return map.remove(key);
    }

    /**
     * Unimplemented. Call keySet() instead
     */
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

}
