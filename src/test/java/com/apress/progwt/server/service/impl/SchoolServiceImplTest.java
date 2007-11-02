package com.apress.progwt.server.service.impl;

import org.apache.log4j.Logger;

import com.apress.progwt.client.domain.Foo;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.SchoolAndAppProcess;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.commands.RemoveSchoolFromRankCommand;
import com.apress.progwt.client.domain.commands.SaveSchoolRankCommand;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.server.dao.SchoolDAO;
import com.apress.progwt.server.dao.UserDAO;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.UserService;

public class SchoolServiceImplTest extends
        AbstractServiceTestWithTransaction {

    private static final Logger log = Logger
            .getLogger(SchoolServiceImplTest.class);
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

    public void testFoo() {

        Foo saved = (Foo) schoolDAO.saveF();

        Foo loaded = (Foo) schoolDAO.get(Foo.class, saved.getId());

        assertEquals(1, loaded.getBarList().size());

    }

    public void testBigSave() {
        School sc = schoolService.getSchoolsMatching("Dartmouth College")
                .get(0);
        assertNotNull(sc);

        User currentUser = getUser();

        assertEquals(0, currentUser.getSchoolRankings().size());

        System.out.println("currentUser " + currentUser);

        SchoolAndAppProcess sap = new SchoolAndAppProcess(sc);
        currentUser.addRanked(sap);

        String NAME = "Mailed";
        ProcessType processType = new ProcessType(NAME);
        currentUser.getProcessTypes().add(processType);

        ProcessValue processValue = new ProcessValue();
        sap.getProcess().put(processType, processValue);

        schoolDAO.save(currentUser);

        User savedUser = userDAO.getUserByUsername(currentUser
                .getUsername());
        assertEquals(1, savedUser.getSchoolRankings().size());

        assertEquals(1, savedUser.getProcessTypes().size());
        ProcessType savedPT = currentUser.getProcessTypes().iterator()
                .next();
        assertEquals(NAME, savedPT.getName());

        SchoolAndAppProcess savedSAP = savedUser.getSchoolRankings().get(
                0);

        ProcessValue savedPValue = savedSAP.getProcess().get(savedPT);

        assertNotNull(savedPValue);
        assertEquals(0.0, savedPValue.getPctComplete());

    }

    /**
     * use this instead of userService.getCurrentUser() because that will
     * save instances between transactions
     * 
     * @return
     */
    private User getUser() {
        User currentUser = userService.getCurrentUser();
        User savedUser = userDAO.getUserByUsernameFetchAll(currentUser
                .getUsername());
        return savedUser;
    }

    public void testBigSaveAgain() {
        log.debug("\n\nSave Again\n\n");
        School sc = schoolService.getSchoolsMatching("Dartmouth College")
                .get(0);
        assertNotNull(sc);

        User currentUser = getUser();
        assertEquals(0, currentUser.getSchoolRankings().size());

        currentUser.addRanked(new SchoolAndAppProcess(sc));

        schoolDAO.save(currentUser);

        User savedUser = userDAO.getUserByUsername(currentUser
                .getUsername());
        assertEquals(1, savedUser.getSchoolRankings().size());

    }

    public void testSaveSchoolRanking() throws SiteException {
        log.debug("\n\nSaveSchoolRankings\n\n");
        School dart = schoolService.getSchoolsMatching("Dartmouth Col")
                .get(0);
        School harvard = schoolService.getSchoolsMatching("Harvard").get(
                0);
        School yale = schoolService.getSchoolsMatching("Yale").get(0);

        // Save in order to Dart/Harvard/Yale
        SaveSchoolRankCommand comm = new SaveSchoolRankCommand(dart, 0);
        schoolService.executeAndSaveCommand(comm, false);

        comm = new SaveSchoolRankCommand(harvard, 1);
        schoolService.executeAndSaveCommand(comm, false);

        comm = new SaveSchoolRankCommand(yale, 2);
        schoolService.executeAndSaveCommand(comm, false);

        User savedUser = getUser();
        assertEquals(3, savedUser.getSchoolRankings().size());

        assertEquals(dart, savedUser.getSchoolRankings().get(0)
                .getSchool());
        assertEquals(harvard, savedUser.getSchoolRankings().get(1)
                .getSchool());
        assertEquals(yale, savedUser.getSchoolRankings().get(2)
                .getSchool());

        log.debug("\n------Re-Order--------");

        // re-order to Dart/Yale/Harvard
        comm = new SaveSchoolRankCommand(harvard, 2);
        schoolService.executeAndSaveCommand(comm, false);

        savedUser = getUser();
        assertEquals(3, savedUser.getSchoolRankings().size());

        assertEquals(dart, savedUser.getSchoolRankings().get(0)
                .getSchool());
        assertEquals(yale, savedUser.getSchoolRankings().get(1)
                .getSchool());
        assertEquals(harvard, savedUser.getSchoolRankings().get(2)
                .getSchool());

    }

    public void testSaveSchoolRankingWithDelete() throws SiteException {

        School dart = schoolService.getSchoolsMatching("Dartmouth Col")
                .get(0);
        School harvard = schoolService.getSchoolsMatching("Harvard").get(
                0);
        School yale = schoolService.getSchoolsMatching("Yale").get(0);

        // Save in order to Dart/Harvard/Yale
        SaveSchoolRankCommand comm = new SaveSchoolRankCommand(dart, 0);
        schoolService.executeAndSaveCommand(comm, false);

        comm = new SaveSchoolRankCommand(harvard, 1);
        schoolService.executeAndSaveCommand(comm, false);

        comm = new SaveSchoolRankCommand(yale, 2);
        schoolService.executeAndSaveCommand(comm, false);

        User currentUser = userService.getCurrentUser();
        User savedUser = userDAO.getUserByUsername(currentUser
                .getUsername());
        assertEquals(3, savedUser.getSchoolRankings().size());

        assertEquals(dart, savedUser.getSchoolRankings().get(0)
                .getSchool());
        assertEquals(harvard, savedUser.getSchoolRankings().get(1)
                .getSchool());
        assertEquals(yale, savedUser.getSchoolRankings().get(2)
                .getSchool());

        // remove middle to Dart/Yale
        RemoveSchoolFromRankCommand comm2 = new RemoveSchoolFromRankCommand(
                harvard);
        schoolService.executeAndSaveCommand(comm2, false);

        savedUser = userDAO.getUserByUsername(currentUser.getUsername());
        assertEquals(2, savedUser.getSchoolRankings().size());

        assertEquals(dart, savedUser.getSchoolRankings().get(0)
                .getSchool());
        assertEquals(yale, savedUser.getSchoolRankings().get(1)
                .getSchool());

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
