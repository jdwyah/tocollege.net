package com.apress.progwt.client.domain.generated;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractProcessValue implements Serializable {

    private double pctComplete;
    private Date dueDate;

    public AbstractProcessValue() {
    }

    public double getPctComplete() {
        return pctComplete;
    }

    public void setPctComplete(double pctComplete) {
        this.pctComplete = pctComplete;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

}
