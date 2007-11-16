package com.apress.progwt.client.domain.generated;

import java.io.Serializable;

public abstract class AbstractRatingType implements Serializable {

    private long id;
    private String name;
    private boolean useByDefault;

    public AbstractRatingType() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isUseByDefault() {
        return useByDefault;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUseByDefault(boolean useByDefault) {
        this.useByDefault = useByDefault;
    }

}
