package com.apress.progwt.server.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.SchoolForumPost;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.dto.SchoolThreads;
import com.apress.progwt.server.dao.UserDAO;

public class SchoolDAOHibernateImplTest extends
        AbstractHibernateTransactionalTest {
    private static final Logger log = Logger
            .getLogger(SchoolDAOHibernateImplTest.class);

    private static final String A = "DUMMY_USERNAME";
    private SchoolDAOHibernateImpl schoolDAO;
    private UserDAO userDAO;

    public void setSchoolDAO(SchoolDAOHibernateImpl schoolDAO) {
        this.schoolDAO = schoolDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void testGetAllSchools() {
        List<School> sc = schoolDAO.getAllSchools();
        assertEquals(2374, sc.size());
    }

    public void testGetSchoolFromName() {
        School sc = schoolDAO.getSchoolFromName("DDFNASD");
        assertNull(sc);

        sc = schoolDAO.getSchoolFromName("Dartmouth College");
        assertNotNull(sc);

    }

    public void testGetSchoolMatching() {
        List<School> res = null;

        res = schoolDAO.getSchoolsMatching("Dartmouth");
        assertEquals(2, res.size());

        int max = 7;
        schoolDAO.setAutoCompleteMax(max);
        res = schoolDAO.getSchoolsMatching("d");
        assertEquals(max, res.size());// default autocomplete max size
        res = schoolDAO.getSchoolsMatching("");
        assertEquals(max, res.size());

        max = 1000;
        schoolDAO.setAutoCompleteMax(max);
        res = schoolDAO.getSchoolsMatching("d");
        assertEquals(748, res.size());// default autocomplete max size
        res = schoolDAO.getSchoolsMatching("");
        assertEquals(max, res.size());

        res = schoolDAO.getSchoolsMatching("d#");
        assertEquals(0, res.size());

        res = schoolDAO.getSchoolsMatching("hav");
        assertEquals(4, res.size());

        res = schoolDAO.getSchoolsMatching("cali");
        assertEquals(54, res.size());

    }

    public void testGetPosts() {

        School sc = schoolDAO
                .getSchoolFromName("Jarvis Christian College");
        assertNotNull(sc);

        // assert that there are no posts for the school
        SchoolThreads threads = schoolDAO.getThreads(sc.getId(), 0, 10);
        assertNotNull(threads);
        assertEquals(0, threads.getTotalCount());
        assertEquals(0, threads.getPosts().size());

        User u = userDAO.getUserByUsername("test");
        assertNotNull(u);

        // Create a first thread for this school
        ForumPost post = new SchoolForumPost(sc, u, A, A, null);
        post.setId(schoolDAO.save(post).getId());

        threads = schoolDAO.getThreads(sc.getId(), 0, 10);
        assertEquals(1, threads.getTotalCount());
        assertEquals(1, threads.getPosts().size());

        ForumPost saved = threads.getPosts().get(0);
        assertNotNull(saved.getDate());
        assertEquals(sc, saved.getSchool());

        // save a second post in the same thread
        ForumPost post2 = new SchoolForumPost(sc, u, null, A, saved);
        post2.setId(schoolDAO.save(post2).getId());

        threads = schoolDAO.getThreads(sc.getId(), 0, 10);
        assertEquals(2, threads.getTotalCount());
        assertEquals(2, threads.getPosts().size());

    }
}
