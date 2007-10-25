package com.apress.progwt.server.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.server.dao.SchoolDAO;

public class SchoolDAOHibernateImpl extends HibernateDaoSupport implements
        SchoolDAO {

    private static final Logger log = Logger
            .getLogger(SchoolDAOHibernateImpl.class);

    public School getSchoolFromName(String name) {
        return (School) DataAccessUtils
                .uniqueResult(getHibernateTemplate().find(
                        "from School where name=?", name));
    }

    public List<School> getAllSchools() {
        return getHibernateTemplate().find("from School");
    }
}
