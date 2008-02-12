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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ListImplementingASet<T> implements Set<T> {

    private List<T> implementingList = new ArrayList<T>();

    public List<T> getList() {
        return implementingList;
    }

    public boolean add(T o) {
        if (!implementingList.contains(o)) {
            return implementingList.add(o);
        }
        return false;
    }

    public void clear() {
        implementingList.clear();
    }

    public boolean contains(Object o) {
        return implementingList.contains(o);
    }

    public boolean isEmpty() {
        return implementingList.isEmpty();
    }

    public Iterator<T> iterator() {
        return implementingList.iterator();
    }

    public boolean remove(Object o) {
        return implementingList.remove(o);
    }

    public int size() {
        return implementingList.size();
    }

    public Object[] toArray() {
        return implementingList.toArray();
    }

    /**
     * Not applicable? List interface doesn't seem to have this in GWT
     * land.
     */
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
        // return implementingList.toArray(a);
    }

    public boolean addAll(Collection<? extends T> c) {
        return implementingList.addAll(c);
    }

    public boolean containsAll(Collection<?> c) {
        return implementingList.containsAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return implementingList.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return implementingList.retainAll(c);
    }

}
