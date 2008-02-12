package com.apress.progwt.client.domain.generated;

import java.io.Serializable;

import com.apress.progwt.client.domain.ApplicationProcess;
import com.apress.progwt.client.domain.School;

public class AbstractSchoolAndAppProcess implements Serializable {
    private long id;
    private School school;
    private ApplicationProcess application;

    public AbstractSchoolAndAppProcess() {
    }

    public School getSchool() {
        return school;
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

}
