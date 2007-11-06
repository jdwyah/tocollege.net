package com.apress.progwt.client.util;

import java.util.ArrayList;
import java.util.List;

import com.apress.progwt.client.domain.commands.Orderable;

import junit.framework.TestCase;

public class UtilitiesTest extends TestCase {
    private class OrderableInteger implements Orderable {

        private int sortOrder;
        private int value;

        public OrderableInteger(int value) {
            this.value = value;
        }

        public void setSortOrder(int sortOrder) {
            this.sortOrder = sortOrder;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + value;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (!(obj instanceof OrderableInteger))
                return false;
            final OrderableInteger other = (OrderableInteger) obj;

            if (value != other.value)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return value + " order " + sortOrder;
        }

    }

    public void testReOrder() {

        List<OrderableInteger> list = new ArrayList<OrderableInteger>();
        list.add(new OrderableInteger(0));
        list.add(new OrderableInteger(1));
        list.add(new OrderableInteger(2));
        list.add(new OrderableInteger(3));
        list.add(new OrderableInteger(4));

        Utilities.reOrder(list, new OrderableInteger(2), 0);

        assertEquals(new OrderableInteger(2), list.get(0));
        assertEquals(new OrderableInteger(0), list.get(1));
        assertEquals(new OrderableInteger(1), list.get(2));
        assertEquals(new OrderableInteger(3), list.get(3));
        assertEquals(new OrderableInteger(4), list.get(4));
        assertEquals(5, list.size());

        Utilities.reOrder(list, new OrderableInteger(0), 2);

        assertEquals(new OrderableInteger(2), list.get(0));
        assertEquals(new OrderableInteger(1), list.get(1));
        assertEquals(new OrderableInteger(0), list.get(2));
        assertEquals(new OrderableInteger(3), list.get(3));
        assertEquals(new OrderableInteger(4), list.get(4));
        assertEquals(5, list.size());

        Utilities.reOrder(list, new OrderableInteger(2), 3);
        assertEquals(new OrderableInteger(1), list.get(0));
        assertEquals(new OrderableInteger(0), list.get(1));
        assertEquals(new OrderableInteger(3), list.get(2));
        assertEquals(new OrderableInteger(2), list.get(3));
        assertEquals(new OrderableInteger(4), list.get(4));
        assertEquals(5, list.size());

        Utilities.reOrder(list, new OrderableInteger(1), 4);
        assertEquals(new OrderableInteger(0), list.get(0));
        assertEquals(new OrderableInteger(3), list.get(1));
        assertEquals(new OrderableInteger(2), list.get(2));
        assertEquals(new OrderableInteger(4), list.get(3));
        assertEquals(new OrderableInteger(1), list.get(4));
        assertEquals(5, list.size());

    }

}
