/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.apress.progwt.server.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.dao.UserDAO;
import com.apress.progwt.server.domain.ServerSideUser;

public class UserDAOHibernateImpl extends HibernateDaoSupport implements
        UserDAO, UserDetailsService {

    private static final Logger log = Logger
            .getLogger(UserDAOHibernateImpl.class);

    // private UserService userService;

    public void delete(User user) {
        getHibernateTemplate().delete(user);
    }

    public List<User> getAllUsers() {
        return getAllUsers(1000);
    }

    public List<User> getAllUsers(int max) {
        DetachedCriteria crit = DetachedCriteria.forClass(User.class);

        List<User> posts = getHibernateTemplate().findByCriteria(crit, 0,
                max);
        return posts;
    }

    /**
     * Uses username.toLowerCase()
     */
    public User getUserByUsername(String username)
            throws UsernameNotFoundException {

        log.debug("Inited");

        List<User> list = getHibernateTemplate().findByNamedParam(
                "from User where lower(username) = :name", "name",
                username.toLowerCase());
        log.debug("list " + list);
        log.debug("list " + list.size());

        if (list.size() != 1) {
            if (!username.equals("anonymousUser")) {
                log.debug("getUserByUsername UsernameNotFoundException "
                        + list.size() + " users for " + username);
            }
            throw new UsernameNotFoundException(
                    "Username not found or duplicate: " + username);
        } else {
            log.debug("load user success " + list.get(0));
            User u = (User) list.get(0);
            log.debug("user: " + u);
            return u;
        }

        // return (User)
        // DataAccessUtils.uniqueResult(getHibernateTemplate().findByNamedParam("from
        // User where username = :name", "name", username));
    }

    public User getUserForId(long id) {
        log.debug("DAOhere user for id " + id);
        return (User) DataAccessUtils.uniqueResult(getHibernateTemplate()
                .findByNamedParam("from User where id = :id", "id", id));
    }

    /**
     * Uses username.toLowerCase()
     */
    public ServerSideUser loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {

        log.debug("here");
        if (username == null) {
            throw new UsernameNotFoundException("Username null not found");
        }
        List<User> users = getHibernateTemplate().findByNamedParam(
                "from User where username = :name", "name",
                username.toLowerCase());

        log.debug("Found " + users.size() + " users for username "
                + username);

        if (users.size() != 1) {
            if (users.size() != 0) {
                throw new UsernameNotFoundException(
                        "Duplicate Username Problem: " + username);
            } else {

                // if OpenID
                // consider returning a ROLE 'TRANSIENT_OpenID' user...

                throw new UsernameNotFoundException(
                        "Username not found: " + username);
            }
        } else {
            log.debug("load user success " + users.get(0));
            User u = users.get(0);
            return new ServerSideUser(u);
        }
    }

    /**
     * Save, ensuring that newly created users have the 'none'
     * subscription first. (non-Javadoc)
     * 
     * @see com.aavu.server.dao.UserDAO#save(com.aavu.client.domain.User)
     */
    public User save(User user) {
        log.warn(user);

        getHibernateTemplate().saveOrUpdate(user);
        return user;
    }

    public User getForPaypalID(String paypalID) {
        return (User) DataAccessUtils.uniqueResult(getHibernateTemplate()
                .findByNamedParam("from User where paypalId = :id", "id",
                        paypalID));
    }

    /**
     * use iterate() to avoid returning rows. Hibernate ref "11.13. Tips &
     * Tricks"
     * 
     * grrrrr... started throwing a classcastexception, but not
     * repeatable..
     */
    public long getUserCount() {
        try {
            return (Long) getHibernateTemplate().iterate(
                    "select count(*) from User").next();
        } catch (ClassCastException e) {
            log.error(e.getMessage());
            return 10000;
        }
    }

    /**
     * add all fetch mode concerns to the critera. without
     * DISTINCT_ROOT_ENTITY these fetches will create multiple rows
     * 
     * If we join both schoolRankings & processTypes, we'll get duplicates
     * again. Fetch one of the collections with a SELECT and initialize
     * instead. Test in UserServiceImpl.testFetch()
     * 
     * Same with the process of each schoolRanking. Note, this is N+1
     * selects.
     * 
     * @param crit
     * @return
     */
    private User fetchAllUser(DetachedCriteria crit) {
        crit.setFetchMode("schoolRankings", FetchMode.JOIN).setFetchMode(
                "schoolRankings.school", FetchMode.JOIN).setFetchMode(
                "schoolRankings.process", FetchMode.SELECT).setFetchMode(
                "processTypes", FetchMode.SELECT).setResultTransformer(
                CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        User rtn = (User) DataAccessUtils
                .uniqueResult(getHibernateTemplate().findByCriteria(crit));
        Hibernate.initialize(rtn.getProcessTypes());
        Hibernate.initialize(rtn.getRatingTypes());
        for (Application application : rtn.getSchoolRankings()) {
            Hibernate.initialize(application.getProcess());
            Hibernate.initialize(application.getRatings());
        }
        
        log.debug("fetched user: "+rtn.getNickname()+" ratings "+rtn.getRatingTypes().size());
        
        // Hibernate.initialize(rtn.getSchoolRankings());
        return rtn;

    }

    public User getUserByNicknameFetchAll(String nickname) {

        return fetchAllUser(DetachedCriteria.forClass(User.class).add(
                Expression.eq("nickname", nickname.toLowerCase())));
    }

    public User getUserByUsernameFetchAll(String username) {
        return fetchAllUser(DetachedCriteria.forClass(User.class).add(
                Expression.eq("username", username)));
    }

}
