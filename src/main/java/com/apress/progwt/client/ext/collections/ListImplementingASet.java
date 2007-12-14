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
