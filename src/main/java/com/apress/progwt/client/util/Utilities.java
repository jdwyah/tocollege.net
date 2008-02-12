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
