package com.apress.progwt.server.service.impl;

import org.apache.log4j.Logger;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.SchoolAndAppProcess;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.exception.BusinessException;
import com.apress.progwt.server.dao.SchoolDAO;
import com.apress.progwt.server.dao.UserDAO;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.UserService;

public class UserServiceImplTest extends
        AbstractServiceTestWithTransaction {

    private static final Logger log = Logger
            .getLogger(UserServiceImplTest.class);
    private SchoolDAO schoolDAO;
    private UserDAO userDAO;
    private SchoolService schoolService;
    private UserService userService;

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        clean();
    }

    private void clean() {

        // TODO Auto-generated method stub

    }

    public void testCreateUser() throws BusinessException {

        userService.createUser("username", "pass", "email", false);

        User saved = userService.getUserWithNormalization("username");

        assertEquals(11, saved.getProcessTypes().size());

        for (ProcessType pType : saved.getProcessTypes()) {
            assertNotNull(pType);
        }
    }

    public void testFetch() throws BusinessException {

        userService.createUser("username", "pass", "email", false);

        User saved = userService.getUserWithNormalization("username");
        School dart = schoolService.getSchoolsMatching(
                "Dartmouth College").get(0);

        saved.addRanked(new SchoolAndAppProcess(dart));
        userDAO.save(saved);

        assertEquals(11, saved.getProcessTypes().size());

        for (ProcessType pType : saved.getProcessTypes()) {
            assertNotNull(pType);
        }

        User fetched = userService.getUserByNicknameFullFetch("username");

        assertEquals(11, fetched.getProcessTypes().size());
        assertEquals(1, fetched.getSchoolRankings().size());

        User fetched2 = userService.getUserByNicknameFullFetch("test");

        assertEquals(11, fetched2.getProcessTypes().size());
        assertEquals(1, fetched2.getSchoolRankings().size());
        assertEquals(4, fetched2.getSchoolRankings().get(0).getProcess()
                .size());
    }

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setSchoolDAO(SchoolDAO schoolDAO) {
        this.schoolDAO = schoolDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

}
