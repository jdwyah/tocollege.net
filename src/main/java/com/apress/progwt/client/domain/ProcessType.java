package com.apress.progwt.client.domain;

import java.io.Serializable;

import com.apress.progwt.client.domain.generated.AbstractProcessType;

public class ProcessType extends AbstractProcessType implements
        Serializable, Loadable {

    public ProcessType() {
    }

    public ProcessType(String name) {
        setName(name);
    }

    @Override
    public String toString() {
        return "ProcessType: " + getName() + " " + getId();
    }

}
