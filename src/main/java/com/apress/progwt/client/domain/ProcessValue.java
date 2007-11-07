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

    public void increment(ProcessType type) {
        if (type.isPercentage()) {
            setPctComplete(getPctComplete() + .25);
            if (getPctComplete() > 1) {
                setPctComplete(0);
            }
        } else {
            if (getPctComplete() == 1) {
                setPctComplete(0);
            } else {
                setPctComplete(1);
            }
        }
    }

    public String getString(ProcessType type) {

        if (type.isPercentage()) {
            return 100 * getPctComplete() + "%";
        } else {
            if (getPctComplete() == 0) {
                return "No";
            } else {
                return "Yes";
            }
        }

    }
}
