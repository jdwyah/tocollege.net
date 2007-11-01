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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    // TODO
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof AbstractApplicationProcess))
            return false;
        final AbstractApplicationProcess other = (AbstractApplicationProcess) obj;
        if (id != other.id)
            return false;
        return true;
    }

    // public ApplicationStatus getStatus() {
    // return status;
    // }
    //
    // public void setStatus(ApplicationStatus status) {
    // this.status = status;
    // }

}
