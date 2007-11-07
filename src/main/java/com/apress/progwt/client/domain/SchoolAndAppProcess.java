package com.apress.progwt.client.domain;

import java.io.Serializable;

import com.apress.progwt.client.domain.commands.Orderable;
import com.apress.progwt.client.domain.generated.AbstractSchoolAndAppProcess;

public class SchoolAndAppProcess extends AbstractSchoolAndAppProcess
        implements Serializable, Loadable, Orderable {

    public SchoolAndAppProcess() {
    }

    public SchoolAndAppProcess(School school) {
        setSchool(school);

    }

    @Override
    public String toString() {
        return "ScAndApp School: " + getSchool() + " User: " + getUser();
    }

    /**
     * Used by FTL templates. See
     * http://fmpp.sourceforge.net/freemarker/app_faq.html#faq_question_12
     * 
     * @param type
     * @return
     */
    public ProcessValue getTheProcess(ProcessType type) {
        return getProcess().get(type);
    }

}
