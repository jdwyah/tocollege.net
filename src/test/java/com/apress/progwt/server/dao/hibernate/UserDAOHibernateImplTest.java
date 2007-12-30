package com.apress.progwt.server.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.dao.UserDAO;
import com.apress.progwt.server.domain.ServerSideUser;

public class UserDAOHibernateImplTest extends
        AbstractHibernateTransactionalTest {
    private static final Logger log = Logger
            .getLogger(UserDAOHibernateImplTest.class);

    private static final String A = "dsafd";
    private static final String B = "324234234";

    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void testGetUserByUsername() {
        String USER = "test";
        User u = userDAO.getUserByUsername(USER);
        assertEquals(USER, u.getUsername());

    }

    public void testLoadUserByUsername() {
        String USER = "test";
        ServerSideUser u = userDAO.loadUserByUsername(USER);
        assertEquals(USER, u.getUsername());

    }

    public void testSave() {

        User u = new User();
        u.setUsername(A);
        u.setPassword(A);

        List<User> list = userDAO.getAllUsers();

        userDAO.save(u);

        User saved = userDAO.getUserByUsername(A);

        assertEquals(A, saved.getUsername());
        assertNotSame(0, saved.getId());
        assertFalse(saved.isSupervisor());
        assertTrue(saved.isEnabled());
        assertTrue(saved.isAccountNonExpired());

        List<User> listPost = userDAO.getAllUsers();

        assertEquals(listPost.size(), list.size() + 1);
        log.debug("User list size " + list.size());
    }

    public void testEdit() {
        String A = "dsafd";
        String B = "sdfn&S*AS";

        User u = new User();
        u.setUsername(A);
        u.setPassword(B);

        List<User> list = userDAO.getAllUsers();

        userDAO.save(u);

        User saved = userDAO.getUserByUsername(A);

        assertEquals(A, saved.getUsername());
        assertNotSame(0, saved.getId());
        assertFalse(saved.isSupervisor());
        assertTrue(saved.isEnabled());
        assertTrue(saved.isAccountNonExpired());

        List<User> listPost = userDAO.getAllUsers();

        assertEquals(listPost.size(), list.size() + 1);
        log.debug("User list size " + list.size());

        // now do some edits
        //
        saved.setUsername(B);
        saved.setSupervisor(true);

        userDAO.save(saved);
        User editted = userDAO.getUserByUsername(B);

        assertNotNull(editted);
        assertEquals(B, editted.getUsername());
        assertSame(saved.getId(), editted.getId());
        assertTrue(saved.isSupervisor());
        assertTrue(saved.isEnabled());
        assertTrue(saved.isAccountNonExpired());

    }

    public void testGetUserCount() {

        List<User> list = userDAO.getAllUsers();

        long pre = userDAO.getUserCount();

        assertEquals(pre, list.size());

        User u = new User();
        u.setUsername(A);
        u.setPassword(B);

        userDAO.save(u);

        assertEquals(pre + 1, userDAO.getUserCount());

    }

    public void testGetUserByNicknameFetchAll() {
        User u = new User();
        u.setUsername(A);
        u.setPassword(A);

        // List<User> list = userDAO.getAllUsers();

        userDAO.save(u);

        User saved = userDAO.getUserByNicknameFetchAll(A);

        assertTrue(Hibernate.isInitialized(saved.getSchoolRankings()));

        User test = userDAO.getUserByNicknameFetchAll("test");
        assertTrue(Hibernate.isInitialized(test.getSchoolRankings()));

        for (Application sap : test.getSchoolRankings()) {
            assertTrue(Hibernate.isInitialized(sap));
            assertTrue(Hibernate.isInitialized(sap.getSchool()));

        }

    }

}
