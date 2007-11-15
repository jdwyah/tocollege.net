package com.apress.progwt.client.domain.generated;

import java.io.Serializable;

public abstract class AbstractRatingType implements Serializable {

    private long id;
    private String name;

    public AbstractRatingType() {
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
