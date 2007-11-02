package com.apress.progwt.server.dao.hibernate;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.apress.progwt.client.domain.Bar;
import com.apress.progwt.client.domain.Foo;
import com.apress.progwt.client.domain.Loadable;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.SchoolAndAppProcess;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.util.Utilities;
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
        DetachedCriteria crit = DetachedCriteria.forClass(School.class);
        List<School> list = getHibernateTemplate().findByCriteria(crit,
                0, 10);
        return list;
    }

    public List<School> getSchoolsMatching(String match) {

        DetachedCriteria crit = DetachedCriteria.forClass(School.class)
                .add(Expression.ilike("name", match, MatchMode.ANYWHERE))
                .addOrder(Order.asc("name"));

        List<School> list = getHibernateTemplate().findByCriteria(crit,
                0, DEFAULT_AUTOCOMPLET_MAX);

        return list;
    }

    public Loadable get(Class<? extends Loadable> loadable, Long id) {
        return (Loadable) getHibernateTemplate().get(loadable, id);
    }

    public Loadable save(Loadable loadable) {
        getHibernateTemplate().saveOrUpdate(loadable);
        return loadable;
    }

    // public void executeAndSaveCommand(final User u,
    // final AbstractCommand command) {
    //
    // getHibernateTemplate().execute(new HibernateCallback() {
    //
    // public Object doInHibernate(Session session)
    // throws HibernateException, SQLException {
    //
    // command.setCurrentUser((User) session.load(User.class, u
    // .getId()));
    //
    // List<Loadable> loadedObjs = new ArrayList<Loadable>(
    // command.getObjects().size());
    // for (Loadable loadable : command.getObjects()) {
    //
    // Loadable l = get(loadable.getClass(), loadable
    // .getId());
    //
    // loadedObjs.add(l);
    // }
    // command.setObjects(loadedObjs);
    //
    // command.executeCommand();
    // return null;
    // }
    // });
    //
    // }

    public Foo saveF() {
        return (Foo) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {

                        Foo f = new Foo("myfoo");

                        session.save(f);

                        Bar b = new Bar("A");

                        b.setFoo(f);
                        f.getBarList().add(b);

                        session.save(f);

                        return f;
                    }
                });

    }

    public void setSchoolAtRank(long userID, School school, int rank) {

        System.out.println("\n----A----\n");
        User currentUser = (User) getHibernateTemplate().get(User.class,
                userID);

        List<SchoolAndAppProcess> rankings = currentUser
                .getSchoolRankings();

        System.out.println("\n----A2----\n");

        // Utilities.reOrder(rankings, currentUser, rank)

        SchoolAndAppProcess sap = null;

        for (Iterator iterator = rankings.iterator(); iterator.hasNext();) {
            SchoolAndAppProcess scAndApp = (SchoolAndAppProcess) iterator
                    .next();

            System.out.println("\n----A3-LOOP----\n");
            if (scAndApp.getSchool().equals(school)) {
                sap = scAndApp;
            }
        }
        System.out.println("\n----B----\n");

        if (null == sap) {
            System.out.println("\n----B-CREATE-NEW-SAP----\n");
            sap = new SchoolAndAppProcess(school);
            getHibernateTemplate().save(sap);
            currentUser.addRanked(rank, sap);
        }

        // use this to set the orderProperty, since the list
        // implementation didn't work
        Utilities.reOrder(rankings, sap, rank);

        System.out.println("\n----C----\n");

        System.out.println("Command Executed");
        System.out.println("User " + currentUser);

        for (SchoolAndAppProcess ranked : currentUser.getSchoolRankings()) {
            System.out.println("Command.Ranks To Save: Rank: " + ranked);
        }
        getHibernateTemplate().save(currentUser);

    }

    public void removeSchool(long userID, School school) {
        User currentUser = (User) getHibernateTemplate().get(User.class,
                userID);
        List<SchoolAndAppProcess> rankings = currentUser
                .getSchoolRankings();

        for (Iterator iterator = rankings.iterator(); iterator.hasNext();) {
            SchoolAndAppProcess scAndApp = (SchoolAndAppProcess) iterator
                    .next();
            if (scAndApp.getSchool().equals(school)) {
                iterator.remove();
            }
        }
        getHibernateTemplate().save(currentUser);
    }

    public List<ProcessType> matchProcessType(String queryString) {
        DetachedCriteria crit = DetachedCriteria.forClass(
                ProcessType.class)
                .add(
                        Expression.ilike("name", queryString,
                                MatchMode.ANYWHERE)).addOrder(
                        Order.asc("name"));

        List<ProcessType> list = getHibernateTemplate().findByCriteria(
                crit, 0, DEFAULT_AUTOCOMPLET_MAX);

        return list;

    }
}
