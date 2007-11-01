package com.apress.progwt.client.domain;

import java.util.ArrayList;
import java.util.List;

public class Foo implements Loadable {
    private long id;
    private String name;
    private List<Bar> barList = new ArrayList<Bar>();

    public Foo(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Bar> getBarList() {
        return barList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBarList(List<Bar> barList) {
        this.barList = barList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Foo))
            return false;
        final Foo other = (Foo) obj;
        if (id != 0 && id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
