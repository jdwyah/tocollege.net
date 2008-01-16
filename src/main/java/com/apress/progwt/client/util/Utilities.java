package com.apress.progwt.client.util;

import java.util.List;

import com.apress.progwt.client.domain.commands.Orderable;

public class Utilities {

    /**
     * Redo the manually maintained sort order of a list when elements
     * have changed place.
     * 
     * @param list
     * @param t
     * @param rank
     */
    public static void reOrder(List list, Orderable t, int rank) {

        list.remove(t);
        if (rank == list.size()) {
            t.setSortOrder(list.size());
            list.add(t);
        } else {
            t.setSortOrder(rank);
            Orderable prev = (Orderable) list.set(rank, t);
            if (prev != null) {
                reOrder(list, prev, rank + 1);
            }
        }
    }

    /**
     * Run this after a removal to ensure that the list has no holes.
     * 
     * @param list
     */
    public static void reOrder(List<? extends Orderable> list) {
        int i = 0;
        for (Orderable object : list) {
            object.setSortOrder(i);
            i++;
        }
    }
}
