package com.apress.progwt.client.domain.generated;

import java.io.Serializable;

public abstract class AbstractProcessType implements Serializable {
    private long id;
    private String name;
    private boolean useByDefault;
    private int status_order;
    private boolean percentage;
    private boolean dated;

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

    public boolean isUseByDefault() {
        return useByDefault;
    }

    public void setUseByDefault(boolean useByDefault) {
        this.useByDefault = useByDefault;
    }

    public int getStatus_order() {
        return status_order;
    }

    public void setStatus_order(int status_order) {
        this.status_order = status_order;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public void setPercentage(boolean percentage) {
        this.percentage = percentage;
    }

    public boolean isDated() {
        return dated;
    }

    public void setDated(boolean dated) {
        this.dated = dated;
    }

}
