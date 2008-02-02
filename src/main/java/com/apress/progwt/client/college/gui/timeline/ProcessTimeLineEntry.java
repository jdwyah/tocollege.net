package com.apress.progwt.client.college.gui.timeline;

import java.util.Date;

import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.gui.timeline.HasDate;

/**
 * Wrap a process type and value together.
 * 
 * @author Jeff Dwyer
 * 
 */
public class ProcessTimeLineEntry implements HasDate {

    private Application application;
    private ProcessType processType;
    private ProcessValue processValue;

    public ProcessTimeLineEntry(Application application,
            ProcessType processType, ProcessValue processValue) {
        super();
        this.processType = processType;
        this.application = application;

        if (processValue == null) {
            this.processValue = new ProcessValue();
        } else {
            this.processValue = processValue;
        }
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public ProcessType getProcessType() {
        return processType;
    }

    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }

    public ProcessValue getProcessValue() {
        return processValue;
    }

    public void setProcessValue(ProcessValue processValue) {
        this.processValue = processValue;
    }

    public Date getEndDate() {
        return getProcessValue().getDueDate();
    }

    public Date getStartDate() {
        return getProcessValue().getDueDate();
    }

    public String getTitle() {
        return getProcessType().getName();
    }

    public void setEndDate(Date newD) {
        getProcessValue().setDueDate(newD);
    }

    public void setStartDate(Date newD) {
        getProcessValue().setDueDate(newD);
    }

}
