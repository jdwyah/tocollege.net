package com.apress.progwt.client.domain.generated;

import java.io.Serializable;
import java.util.Map;

import com.apress.progwt.client.domain.ApplicationCheckbox;

public class AbstractApplicationProcess implements Serializable {
    private long id;
    private Map<ApplicationCheckbox, Integer> applicationItems;

    // private ApplicationStatus status;

    public AbstractApplicationProcess() {
    }

    public Map<ApplicationCheckbox, Integer> getApplicationItems() {
        return applicationItems;
    }

    public void setApplicationItems(
            Map<ApplicationCheckbox, Integer> applicationItems) {
        this.applicationItems = applicationItems;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // public ApplicationStatus getStatus() {
    // return status;
    // }
    //
    // public void setStatus(ApplicationStatus status) {
    // this.status = status;
    // }

}
