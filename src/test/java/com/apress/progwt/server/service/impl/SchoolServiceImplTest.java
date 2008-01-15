package com.apress.progwt.server.service.impl;

import java.util.Calendar;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.Foo;
import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.RatingType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.SchoolForumPost;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.commands.RemoveSchoolFromRankCommand;
import com.apress.progwt.client.domain.commands.SaveForumPostCommand;
import com.apress.progwt.client.domain.commands.SaveProcessCommand;
import com.apress.progwt.client.domain.commands.SaveRatingCommand;
import com.apress.progwt.client.domain.commands.SaveSchoolRankCommand;
import com.apress.progwt.client.domain.dto.PostsList;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.server.dao.SchoolDAO;
import com.apress.progwt.server.dao.UserDAO;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.UserService;

public class SchoolServiceImplTest extends
        AbstractServiceTestWithTransaction {

    private static final Logger log = Logger
            .getLogger(SchoolServiceImplTest.class);
    private static final String TITLE = "test title";
    private static final String TEXT = "test textaroo<b>with bold</b>";
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

        School sc = schoolService.getSchoolDetails("Dartmouth College");
        assertNotNull(sc);

        User currentUser = getUser();

        assertEquals(0, currentUser.getSchoolRankings().size());

        System.out.println("currentUser " + currentUser);

        Application sap = new Application(sc);
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

        assertEquals(12, savedUser.getProcessTypes().size());

        ProcessType savedPT = null;
        // iterate to the last one
        Iterator i = currentUser.getProcessTypes().iterator();
        while (i.hasNext()) {
            savedPT = (ProcessType) i.next();
        }
        assertEquals(NAME, savedPT.getName());

        Application savedSAP = savedUser.getSchoolRankings().get(0);

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
        School sc = schoolService.getSchoolDetails("Dartmouth College");
        assertNotNull(sc);

        User currentUser = getUser();
        assertEquals(0, currentUser.getSchoolRankings().size());

        currentUser.addRanked(new Application(sc));

        schoolDAO.save(currentUser);

        User savedUser = userDAO.getUserByUsername(currentUser
                .getUsername());
        assertEquals(1, savedUser.getSchoolRankings().size());

    }

    public void testSaveSchoolRanking() throws SiteException {
        log.debug("\n\nSaveSchoolRankings\n\n");
        School dart = schoolService.getSchoolDetails("Dartmouth College");
        School harvard = schoolService
                .getSchoolDetails("Harvard University");
        School yale = schoolService.getSchoolDetails("Yale University");
        assertNotNull(dart);
        assertNotNull(harvard);
        assertNotNull(yale);

        // Save in order to Dart/Harvard/Yale
        SaveSchoolRankCommand comm = new SaveSchoolRankCommand(dart,
                getUser(), 0);
        schoolService.executeAndSaveCommand(comm, false);

        comm = new SaveSchoolRankCommand(harvard, getUser(), 1);
        schoolService.executeAndSaveCommand(comm, false);

        comm = new SaveSchoolRankCommand(yale, getUser(), 2);
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

        // re-order to Dart,Yale,Harvard
        comm = new SaveSchoolRankCommand(harvard, getUser(), 2);
        schoolService.executeAndSaveCommand(comm, false);

        savedUser = getUser();
        assertEquals(3, savedUser.getSchoolRankings().size());

        assertEquals(dart, savedUser.getSchoolRankings().get(0)
                .getSchool());
        assertEquals(yale, savedUser.getSchoolRankings().get(1)
                .getSchool());
        assertEquals(harvard, savedUser.getSchoolRankings().get(2)
                .getSchool());

        // re-order to Harvard,Dart,Yale
        comm = new SaveSchoolRankCommand(harvard, getUser(), 0);
        schoolService.executeAndSaveCommand(comm, false);

        savedUser = getUser();
        assertEquals(3, savedUser.getSchoolRankings().size());

        assertEquals(harvard, savedUser.getSchoolRankings().get(0)
                .getSchool());
        assertEquals(dart, savedUser.getSchoolRankings().get(1)
                .getSchool());
        assertEquals(yale, savedUser.getSchoolRankings().get(2)
                .getSchool());

    }

    public void testSaveSchoolRankingWithDelete() throws SiteException {

        School dart = schoolService.getSchoolDetails("Dartmouth College");
        School harvard = schoolService
                .getSchoolDetails("Harvard University");
        School yale = schoolService.getSchoolDetails("Yale University");

        // Save in order to Dart/Harvard/Yale
        SaveSchoolRankCommand comm = new SaveSchoolRankCommand(dart,
                getUser(), 0);
        schoolService.executeAndSaveCommand(comm, false);

        comm = new SaveSchoolRankCommand(harvard, getUser(), 1);
        schoolService.executeAndSaveCommand(comm, false);

        comm = new SaveSchoolRankCommand(yale, getUser(), 2);
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
                harvard, getUser());
        schoolService.executeAndSaveCommand(comm2, false);

        savedUser = userDAO.getUserByUsername(currentUser.getUsername());
        assertEquals(2, savedUser.getSchoolRankings().size());

        assertEquals(dart, savedUser.getSchoolRankings().get(0)
                .getSchool());
        assertEquals(yale, savedUser.getSchoolRankings().get(1)
                .getSchool());

    }

    public void testSaveProcessTypes() throws SiteException {
        log.debug("\ntestSaveProcessTypes\n\n");
        School dart = schoolService.getSchoolDetails("Dartmouth College");
        School harvard = schoolService
                .getSchoolDetails("Harvard University");
        School yale = schoolService.getSchoolDetails("Yale University");

        // Save in order to Dart/Harvard/Yale
        SaveSchoolRankCommand comm = new SaveSchoolRankCommand(dart,
                getUser(), 0);
        schoolService.executeAndSaveCommand(comm, false);

        comm = new SaveSchoolRankCommand(harvard, getUser(), 1);
        schoolService.executeAndSaveCommand(comm, false);

        comm = new SaveSchoolRankCommand(yale, getUser(), 2);
        schoolService.executeAndSaveCommand(comm, false);

        User savedUser = getUser();
        assertEquals(3, savedUser.getSchoolRankings().size());

        Application dartApplication = savedUser.getSchoolRankings()
                .get(0);

        assertEquals(dart, dartApplication.getSchool());

        ProcessType considering = getUser().getProcessTypes().get(0);

        ProcessValue processValue = new ProcessValue();
        Calendar d = Calendar.getInstance();
        d.set(2005, 3, 10);
        processValue.setDueDate(d.getTime());
        processValue.setPctComplete(.34);

        // re-order to Harvard,Dart,Yale
        SaveProcessCommand processComm = new SaveProcessCommand(
                dartApplication, considering, processValue);
        schoolService.executeAndSaveCommand(processComm, false);

        User savedUser2 = getUser();

        Application savedDart = savedUser2.getSchoolRankings().get(0);
        ProcessValue savedValue = savedDart.getProcess().get(considering);

        assertEquals(.34, savedValue.getPctComplete());
        assertEquals(2005, savedValue.getDueDate().getYear() + 1900);

    }

    public void testSaveRatingTypes() throws SiteException {
        log.debug("\n\nSaveratingTypes\n\n");
        School dart = schoolService.getSchoolDetails("Dartmouth College");
        School harvard = schoolService
                .getSchoolDetails("Harvard University");
        School yale = schoolService.getSchoolDetails("Yale University");

        // Save in order to Dart/Harvard/Yale
        SaveSchoolRankCommand comm = new SaveSchoolRankCommand(dart,
                getUser(), 0);
        schoolService.executeAndSaveCommand(comm, false);

        comm = new SaveSchoolRankCommand(harvard, getUser(), 1);
        schoolService.executeAndSaveCommand(comm, false);

        comm = new SaveSchoolRankCommand(yale, getUser(), 2);
        schoolService.executeAndSaveCommand(comm, false);

        User savedUser = getUser();
        assertEquals(3, savedUser.getSchoolRankings().size());

        Application dartApplication = savedUser.getSchoolRankings()
                .get(0);

        assertEquals(dart, dartApplication.getSchool());

        RatingType ratingOne = getUser().getRatingTypes().get(0);
        RatingType ratingTwo = getUser().getRatingTypes().get(1);

        SaveRatingCommand command = new SaveRatingCommand(ratingOne, 3,
                dartApplication);

        schoolService.executeAndSaveCommand(command, false);

        User savedUser2 = getUser();

        Application savedDart = savedUser2.getSchoolRankings().get(0);

        assertEquals(3, savedDart.getRating(ratingOne));
        assertEquals(5, savedDart.getRating(ratingTwo));

    }

    public void testForumReplies() {
        PostsList posts = schoolService.getSchoolThreads(500, 0, 10);

        for (ForumPost fp : posts.getPosts()) {
            System.out.println("fp: " + fp + " REPL "
                    + fp.getReplies().size());
        }
    }

    public void testForumPostSaving() throws SiteException {
        log.debug("\n\nSave Again\n\n");
        School sc = schoolService.getSchoolDetails("Adrian College");
        assertNotNull(sc);

        User currentUser = getUser();
        assertNotNull(currentUser);

        ForumPost fp = new SchoolForumPost(sc, currentUser, TITLE, TEXT,
                null);

        schoolService.executeAndSaveCommand(new SaveForumPostCommand(fp));

        PostsList posts = schoolService.getSchoolThreads(sc.getId(), 0,
                10);

        assertEquals(1, posts.getTotalCount());
        assertEquals(1, posts.getPosts().size());

        ForumPost saved = posts.getPosts().get(0);
        assertNotNull(saved);
        assertEquals(SchoolForumPost.class, saved.getClass());
        assertTrue(saved.getId() > 0);
        assertEquals(TITLE, saved.getPostTitle());
        assertEquals(TEXT, saved.getPostString());
        assertEquals(0, saved.getReplyCount());

        assertEquals(null, saved.getThreadPost());

        assertEquals(currentUser, saved.getAuthor());

        // save a second post to the same thread
        ForumPost fp2 = new SchoolForumPost(sc, currentUser, TITLE, TEXT,
                saved);
        schoolService
                .executeAndSaveCommand(new SaveForumPostCommand(fp2));

        // assert that there's still just 1 top level thread
        posts = schoolService.getSchoolThreads(sc.getId(), 0, 10);
        assertEquals(1, posts.getTotalCount());
        assertEquals(1, posts.getPosts().size());

        // relies on setting the inverse side of the association for
        // testing
        assertEquals(1, posts.getPosts().get(0).getReplyCount());
        assertEquals(1, saved.getReplyCount());

        // get the posts in this thread
        posts = schoolService.getPostsForThread(saved, 0, 10);
        assertEquals(2, posts.getTotalCount());
        assertEquals(2, posts.getPosts().size());

        ForumPost saved1 = posts.getPosts().get(0);
        assertEquals(saved, saved1);
        ForumPost saved2 = posts.getPosts().get(1);
        assertNotNull(saved2);
        assertEquals(SchoolForumPost.class, saved2.getClass());
        assertTrue(saved2.getId() > 0);
        assertEquals(TITLE, saved2.getPostTitle());
        assertEquals(TEXT, saved2.getPostString());

        assertEquals(saved1, saved2.getThreadPost());
        assertEquals(currentUser, saved2.getAuthor());

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
