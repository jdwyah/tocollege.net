package com.apress.progwt.server.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.apress.progwt.client.domain.Loadable;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.server.dao.SchoolDAO;

public class SchoolDAOHibernateImpl extends HibernateDaoSupport implements

SchoolDAO {

    private static final Logger log = Logger
            .getLogger(SchoolDAOHibernateImpl.class);
    private static final int DEFAULT_AUTOCOMPLET_MAX = 7;

    public School getSchoolFromName(String name) {
        return (School) DataAccessUtils
                .uniqueResult(getHibernateTemplate().find(
                        "from School where name=?", name));
    }

    public List<School> getAllSchools() {
        return getHibernateTemplate().find("from School");
    }

    public List<School> getSchoolsMatching(String match) {

        DetachedCriteria crit = DetachedCriteria.forClass(School.class)
                .add(Expression.ilike("name", match, MatchMode.ANYWHERE))
                .addOrder(Order.asc("name"));

        List<School> list = getHibernateTemplate().findByCriteria(crit,
                0, DEFAULT_AUTOCOMPLET_MAX);

        return list;
    }

    public Loadable get(Class<Loadable> loadable, Long id) {
        return (Loadable) getHibernateTemplate().get(loadable, id);
    }

    public void save(Loadable loadable) {
        getHibernateTemplate().save(loadable);
    }
}
