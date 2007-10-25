package com.apress.progwt.server.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.server.dao.SchoolDAO;
import com.apress.progwt.server.domain.SchoolAndRank;
import com.apress.progwt.server.service.SchoolService;

public class SchoolServiceImpl implements SchoolService {

    private static final Logger log = Logger
            .getLogger(SchoolServiceImpl.class);

    private SchoolDAO schoolDAO;

    public School getSchoolDetails(String schoolname) {

        return schoolDAO.getSchoolFromName(schoolname);
    }

    @Required
    public void setSchoolDAO(SchoolDAO schoolDAO) {
        this.schoolDAO = schoolDAO;
    }

    public List<SchoolAndRank> getPopularSchools() {
        List<SchoolAndRank> ranked = new LinkedList<SchoolAndRank>();
        for (School school : getTopSchools()) {
            ranked
                    .add(new SchoolAndRank(school,
                            Math.random() * 5 - 2.5));
        }
        return ranked;
    }

    public List<School> getTopSchools() {
        return schoolDAO.getAllSchools().subList(0, 10);
    }
}
