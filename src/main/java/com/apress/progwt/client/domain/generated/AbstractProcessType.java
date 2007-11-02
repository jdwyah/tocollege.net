package com.apress.progwt.client.domain.generated;

import java.io.Serializable;

public class AbstractProcessType implements Serializable {
    private long id;
    private String name;

    public AbstractProcessType() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
