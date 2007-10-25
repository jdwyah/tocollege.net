package com.apress.progwt.server.dao.hibernate;

import java.util.List;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.server.dao.SchoolDAO;

public class SchoolDAOHibernateImplTest extends
        HibernateTransactionalTest {

    private SchoolDAO schoolDAO;

    public void testGetSchoolFromName() {
        School sc = schoolDAO.getSchoolFromName("DDFNASD");
        assertNull(sc);

        sc = schoolDAO.getSchoolFromName("Dartmouth College");
        assertNotNull(sc);

    }

    public void testGetAllSchools() {
        List<School> sc = schoolDAO.getAllSchools();
        assertEquals(2374, sc.size());
    }

    public void setSchoolDAO(SchoolDAO schoolDAO) {
        this.schoolDAO = schoolDAO;
    }

}
