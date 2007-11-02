package com.apress.progwt.client.domain;

import java.io.Serializable;
import java.util.Date;

import com.apress.progwt.client.domain.generated.AbstractProcessValue;

public class ProcessValue extends AbstractProcessValue implements
        Serializable {

    public ProcessValue() {
        setDueDate(new Date());
        setPctComplete(0);
    }

    public void increment() {
        setPctComplete(getPctComplete() + .25);
    }

    public String getString() {
        return getPctComplete() + "%";
    }

}
