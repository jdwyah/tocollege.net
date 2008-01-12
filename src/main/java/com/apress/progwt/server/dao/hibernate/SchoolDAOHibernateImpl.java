package com.apress.progwt.server.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.apress.progwt.client.domain.Bar;
import com.apress.progwt.client.domain.Foo;
import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.Loadable;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.RatingType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.dto.PostsList;
import com.apress.progwt.server.dao.SchoolDAO;

public class SchoolDAOHibernateImpl extends HibernateDaoSupport implements
        SchoolDAO {

    private int autoCompleteMax = 7;

    private static final Logger log = Logger
            .getLogger(SchoolDAOHibernateImpl.class);

    public Loadable get(Class<? extends Loadable> loadable, Long id) {
        return (Loadable) getHibernateTemplate().get(loadable, id);
    }

    public List<School> getAllSchools() {
        DetachedCriteria crit = DetachedCriteria.forClass(School.class);
        // .add(
        // Expression.and(Expression.gt("id", 890l),
        // Expression.eq("latitude", -1d)));
        List<School> list = getHibernateTemplate().findByCriteria(crit);
        return list;
    }

    public List<ProcessType> getDefaultProcessTypes() {
        DetachedCriteria crit = DetachedCriteria.forClass(
                ProcessType.class).add(
                Expression.eq("useByDefault", true)).addOrder(
                Order.asc("id"));

        return getHibernateTemplate().findByCriteria(crit);
    }

    public List<RatingType> getDefaultRatingTypes() {
        DetachedCriteria crit = DetachedCriteria.forClass(
                RatingType.class)
                .add(Expression.eq("useByDefault", true)).addOrder(
                        Order.asc("id"));

        return getHibernateTemplate().findByCriteria(crit);
    }

    public ProcessType getProcessForName(String string) {
        DetachedCriteria crit = DetachedCriteria.forClass(
                ProcessType.class).add(Expression.eq("name", string));

        return (ProcessType) DataAccessUtils
                .uniqueResult(getHibernateTemplate().findByCriteria(crit));
    }

    public School getSchoolFromName(String name) {
        return (School) DataAccessUtils
                .uniqueResult(getHibernateTemplate().find(
                        "from School where name=?", name));
    }

    public List<School> getSchoolsMatching(String match) {

        DetachedCriteria crit = DetachedCriteria.forClass(School.class)
                .add(Expression.ilike("name", match, MatchMode.ANYWHERE))
                .addOrder(Order.asc("name"));

        List<School> list = getHibernateTemplate().findByCriteria(crit,
                0, autoCompleteMax);

        return list;
    }

    public List<ProcessType> matchProcessType(String queryString) {
        DetachedCriteria crit = DetachedCriteria.forClass(
                ProcessType.class)
                .add(
                        Expression.ilike("name", queryString,
                                MatchMode.ANYWHERE)).addOrder(
                        Order.asc("name"));

        List<ProcessType> list = getHibernateTemplate().findByCriteria(
                crit, 0, autoCompleteMax);

        return list;

    }

    public Loadable save(Loadable loadable) {
        getHibernateTemplate().saveOrUpdate(loadable);
        return loadable;
    }

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

    /**
     * Change the number of autocomplete matches that are returned.
     * 
     * @param autoCompleteMax
     */
    public void setAutoCompleteMax(int autoCompleteMax) {
        this.autoCompleteMax = autoCompleteMax;
    }

    public int getAutoCompleteMax() {
        return autoCompleteMax;
    }

    /**
     * get the total number of rows without actually returning all rows
     * NOTE: important to set the start row here, otherwise when we start
     * paging, this criteria will be affected and we won't get the first
     * row.
     * 
     * @param criteria
     * @return
     */
    private int getRowCount(DetachedCriteria criteria) {
        criteria.setProjection(Projections.rowCount());
        return ((Integer) getHibernateTemplate().findByCriteria(criteria,
                0, 1).get(0)).intValue();
    }

    /**
     * 
     * Posts that are threads will have their threadPost field == null.
     * 
     * Could try to optimize by getting close to: SELECT f.id,
     * f.thread_id, count(s.id) FROM `forumposts` f left JOIN forumposts s
     * on f.id = s.thread_id WHERE f.thread_id IS NULL group by f.id
     * 
     */
    public PostsList getSchoolThreads(long schoolID, int start, int max) {

        System.out.println("start " + start + " max " + max);

        DetachedCriteria crit = DetachedCriteria.forClass(
                ForumPost.class, "fp").add(
                Expression.and(Expression.eq("school.id", schoolID),
                        Expression.isNull("threadPost"))).addOrder(
                Order.desc("date"));

        List<ForumPost> posts = getHibernateTemplate().findByCriteria(
                crit, start, max);

        // set the reply count so we can have this information w/o loading
        // the actual set.
        for (ForumPost fp : posts) {
            fp.setReplyCount(fp.getReplies().size());
        }

        PostsList rtn = new PostsList(posts, getRowCount(crit));

        return rtn;
    }

    public PostsList getUserThreads(long userID, int start, int max) {
        DetachedCriteria crit = DetachedCriteria
                .forClass(ForumPost.class).add(
                        Expression.and(Expression.eq("user.id", userID),
                                Expression.isNull("threadPost")))
                .addOrder(Order.desc("date"));

        List<ForumPost> posts = getHibernateTemplate().findByCriteria(
                crit, start, max);

        PostsList rtn = new PostsList(posts, getRowCount(crit));

        return rtn;
    }

    public PostsList getPostsForThread(ForumPost post, int start, int max) {
        DetachedCriteria crit = DetachedCriteria
                .forClass(ForumPost.class).add(
                        Expression.or(Expression.eq("threadPost.id", post
                                .getId()), Expression.eq("id", post
                                .getId()))).addOrder(Order.asc("date"));

        List<ForumPost> posts = getHibernateTemplate().findByCriteria(
                crit, start, max);

        PostsList rtn = new PostsList(posts, getRowCount(crit));

        return rtn;
    }
}
