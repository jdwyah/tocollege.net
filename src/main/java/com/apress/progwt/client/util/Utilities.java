package com.apress.progwt.client.util;

import java.util.List;

import com.apress.progwt.client.domain.commands.Orderable;

public class Utilities {

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
}
