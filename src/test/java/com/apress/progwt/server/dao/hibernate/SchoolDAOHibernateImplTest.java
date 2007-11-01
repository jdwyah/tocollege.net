package com.apress.progwt.server.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.server.dao.SchoolDAO;
import com.apress.progwt.server.dao.UserDAO;

public class SchoolDAOHibernateImplTest extends
        AbstractHibernateTransactionalTest {
    private static final Logger log = Logger
            .getLogger(SchoolDAOHibernateImplTest.class);

    private static final String A = "DUMMY_USERNAME";
    private SchoolDAO schoolDAO;
    private UserDAO userDAO;

    public void setSchoolDAO(SchoolDAO schoolDAO) {
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

}
