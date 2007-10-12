package com.apress.progwt.server.dao.hibernate;

import java.util.List;

import org.acegisecurity.AuthenticationException;
import org.acegisecurity.providers.cas.CasAuthoritiesPopulator;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.dao.UserDAO;
import com.apress.progwt.server.domain.ServerSideUser;
import com.apress.progwt.server.service.UserService;

public class UserDAOHibernateImpl extends HibernateDaoSupport implements UserDAO,
		UserDetailsService, CasAuthoritiesPopulator {

	private static final Logger log = Logger.getLogger(UserDAOHibernateImpl.class);


	private UserService userService;


	public void delete(User user) {
		getHibernateTemplate().delete(user);
	}

	public List<User> getAllUsers() {
		return getHibernateTemplate().find("from User");
	}

	/**
	 * Uses username.toLowerCase()
	 */
	public User getUserByUsername(String username) throws UsernameNotFoundException {

		log.debug("Inited");

		List<User> list = getHibernateTemplate().findByNamedParam(
				"from User where username = :name", "name", username.toLowerCase());
		log.debug("list " + list);
		log.debug("list " + list.size());

		if (list.size() != 1) {
			if (!username.equals("anonymousUser")) {
				log.debug("getUserByUsername UsernameNotFoundException " + list.size()
						+ " users for " + username);
			}
			throw new UsernameNotFoundException("Username not found or duplicate.");
		} else {
			log.debug("load user success " + list.get(0));
			User u = (User) list.get(0);
			log.debug("user: " + u);
			return u;
		}


		// return (User) DataAccessUtils.uniqueResult(getHibernateTemplate().findByNamedParam("from
		// User where username = :name", "name", username));
	}

	public User getUserForId(long id) {
		log.debug("DAOhere user for id " + id);
		return (User) DataAccessUtils.uniqueResult(getHibernateTemplate().findByNamedParam(
				"from User where id = :id", "id", id));
	}

	/**
	 * Uses username.toLowerCase()
	 */
	public ServerSideUser loadUserByUsername(String username) throws UsernameNotFoundException,
			DataAccessException {

		log.debug("here");
		if (username == null) {
			throw new UsernameNotFoundException("Username null not found");
		}
		List<User> users = getHibernateTemplate().findByNamedParam(
				"from User where username = :name", "name", username.toLowerCase());

		log.debug("Found " + users.size() + " users for username " + username);

		if (users.size() != 1) {
			if (users.size() != 0) {
				throw new UsernameNotFoundException("Duplicate Username Problem: " + username);
			} else {
				throw new UsernameNotFoundException("Username not found: " + username);
			}
		} else {
			log.debug("load user success " + users.get(0));
			User u = (User) users.get(0);
			return new ServerSideUser(u);
		}
	}

	/**
	 * Save, ensuring that newly created users have the 'none' subscription first. (non-Javadoc)
	 * 
	 * @see com.aavu.server.dao.UserDAO#save(com.aavu.client.domain.User)
	 */
	public User save(User user) {
		log.warn(user);

		getHibernateTemplate().saveOrUpdate(user);
		return user;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public User getForPaypalID(String paypalID) {
		return (User) DataAccessUtils.uniqueResult(getHibernateTemplate().findByNamedParam(
				"from User where paypalId = :id", "id", paypalID));
	}


	public UserDetails getUserDetails(String username) throws AuthenticationException {
		log.debug("getting userdetails " + username);

		return loadUserByUsername(username);

	}

	/**
	 * use iterate() to avoid returning rows. Hibernate ref "11.13. Tips & Tricks"
	 * 
	 * grrrrr... started throwing a classcastexception, but not repeatable..
	 */
	public long getUserCount() {
		try {
			return (Long) getHibernateTemplate().iterate("select count(*) from User").next();
		} catch (ClassCastException e) {
			log.error(e.getMessage());
			return 10000;
		}
	}



}
