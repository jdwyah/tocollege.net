package com.apress.progwt.client.domain.generated;

import java.io.Serializable;

import com.apress.progwt.client.domain.ApplicationProcess;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;

public class AbstractSchoolAndAppProcess implements Serializable {

    private long id;
    private School school;
    private ApplicationProcess application;
    private User user;

    private int sortOrder;

    public AbstractSchoolAndAppProcess() {
    }

    public School getSchool() {
        return school;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public ApplicationProcess getApplication() {
        return application;
    }

    public void setApplication(ApplicationProcess application) {
        this.application = application;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((application == null) ? 0 : application.hashCode());
        result = prime * result
                + ((school == null) ? 0 : school.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof AbstractSchoolAndAppProcess))
            return false;
        final AbstractSchoolAndAppProcess other = (AbstractSchoolAndAppProcess) obj;
        if (application == null) {
            if (other.application != null)
                return false;
        } else if (!application.equals(other.application))
            return false;
        if (school == null) {
            if (other.school != null)
                return false;
        } else if (!school.equals(other.school))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

}
