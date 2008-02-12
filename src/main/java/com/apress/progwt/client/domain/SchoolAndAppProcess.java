package com.apress.progwt.client.domain;

import java.io.Serializable;

import com.apress.progwt.client.domain.generated.AbstractSchoolAndAppProcess;

public class SchoolAndAppProcess extends AbstractSchoolAndAppProcess
        implements Serializable, Loadable {

    public SchoolAndAppProcess() {
    }

    public SchoolAndAppProcess(School school) {
        setSchool(school);
        setApplication(new ApplicationProcess());
    }

}
