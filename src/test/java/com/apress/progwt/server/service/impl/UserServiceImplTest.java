package com.apress.progwt.server.service.impl;

import org.apache.log4j.Logger;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.UrlIdentifier;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.test.AssertThrows;

import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.RatingType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.server.dao.SchoolDAO;
import com.apress.progwt.server.dao.UserDAO;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.UserService;

public class UserServiceImplTest extends
        AbstractServiceTestWithTransaction {

    private static final Logger log = Logger
            .getLogger(UserServiceImplTest.class);

    private static final int PROCESS_TYPES = 8;

    private static final int RATING_TYPES = 5;

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

    public void testCreateUser() throws UsernameNotFoundException,
            SiteException {

        userService.createUser("username", "pass", "email", false);

        User saved = userService.getUserWithNormalization("username");

        assertEquals(PROCESS_TYPES, saved.getProcessTypes().size());

        for (ProcessType pType : saved.getProcessTypes()) {
            assertNotNull(pType);
        }

        assertEquals(RATING_TYPES, saved.getRatingTypes().size());

        for (RatingType rType : saved.getRatingTypes()) {
            assertNotNull(rType);
        }
    }

    public void testFetch() throws UsernameNotFoundException,
            SiteException {

        userService.createUser("username", "pass", "email", false);

        User saved = userService.getUserWithNormalization("username");
        School dart = schoolService.getSchoolDetails("Dartmouth College");

        saved.addRanked(new Application(dart));
        userDAO.save(saved);

        assertEquals(PROCESS_TYPES, saved.getProcessTypes().size());

        for (ProcessType pType : saved.getProcessTypes()) {
            assertNotNull(pType);
        }

        User fetched = userService.getUserByNicknameFullFetch("username");

        assertEquals(PROCESS_TYPES, fetched.getProcessTypes().size());
        assertEquals(1, fetched.getSchoolRankings().size());

        User fetched2 = userService.getUserByNicknameFullFetch("test");

        assertEquals(PROCESS_TYPES, fetched2.getProcessTypes().size());
        assertEquals(RATING_TYPES, fetched2.getRatingTypes().size());
        assertEquals(1, fetched2.getSchoolRankings().size());
        assertEquals(4, fetched2.getSchoolRankings().get(0).getProcess()
                .size());
    }

    public void testOpenIDNormalize() throws DiscoveryException {

        assertEquals("http://factoryjoe.com/", UrlIdentifier.normalize(
                "http://factoryjoe.com").toExternalForm());
        assertEquals("http://factoryjoe.com/", UrlIdentifier.normalize(
                "http://factoryjoe.com/").toExternalForm());
        assertEquals("http://www.factoryjoe.com/", UrlIdentifier
                .normalize("http://www.factoryjoe.com").toExternalForm());

        // show that current is unsatisfactory
        new AssertThrows(IllegalArgumentException.class) {
            public void test() throws Exception {
                assertEquals("http://factoryjoe.com/", UrlIdentifier
                        .normalize("factoryjoe.com").toExternalForm());
            }
        };

    }

    public void testNormalizeUrl() throws SiteException {

        System.out.println("testNormalizeUrl");

        assertEquals("http://foo.com/", UserServiceImpl
                .normalizeUrl("foo.com"));

        assertEquals("http://foo.com/", UserServiceImpl
                .normalizeUrl("http://foo.com"));
        assertEquals("https://foo.com/", UserServiceImpl
                .normalizeUrl("https://foo.com"));
        assertEquals("http://foo.com/bar", UserServiceImpl
                .normalizeUrl("foo.com/bar"));
        assertEquals("http://foo.com/bar", UserServiceImpl
                .normalizeUrl("http://foo.com/bar"));

        assertEquals("http://foo.com/", UserServiceImpl
                .normalizeUrl("http://foo.com/"));
        assertEquals("https://foo.com/", UserServiceImpl
                .normalizeUrl("https://foo.com/"));
        assertEquals("https://foo.com/bar", UserServiceImpl
                .normalizeUrl("https://foo.com/bar"));

        AssertThrows at = new AssertThrows(SiteException.class) {
            @Override
            public void test() throws Exception {
                assertNull(UserServiceImpl.normalizeUrl(""));
            }
        };
        at = new AssertThrows(SiteException.class) {
            @Override
            public void test() throws Exception {
                assertNull(UserServiceImpl.normalizeUrl("http://"));
            }
        };
        at = new AssertThrows(SiteException.class) {
            @Override
            public void test() throws Exception {
                assertNull(UserServiceImpl.normalizeUrl(null));
            }
        };

    }

    public void skip_testNormalizeURL() throws SiteException {
        assertEquals("http://foo.com/%E8%8D%89", UserServiceImpl
                .normalizeUrl("foo.com/\u8349"));
        assertEquals("http://foo.com/%E8%8D%89", UserServiceImpl
                .normalizeUrl("http://foo.com/\u8349"));
        assertEquals("http://xn--vl1a.com/", UserServiceImpl
                .normalizeUrl("\u8349.com"));
        assertEquals("http://xn--vl1a.com/", UserServiceImpl
                .normalizeUrl("http://\u8349.com"));
        assertEquals("http://xn--vl1a.com/", UserServiceImpl
                .normalizeUrl("\u8349.com/"));
        assertEquals("http://xn--vl1a.com/", UserServiceImpl
                .normalizeUrl("http://\u8349.com/"));
        assertEquals("http://xn--vl1a.com/%E8%8D%89", UserServiceImpl
                .normalizeUrl("\u8349.com/\u8349"));
        assertEquals("http://xn--vl1a.com/%E8%8D%89", UserServiceImpl
                .normalizeUrl("http://\u8349.com/\u8349"));
    }

    public void testToken() throws SiteException {
        String nullT = userService.getToken(null);

        User fetched2 = userService.getUserByNicknameFullFetch("test");
        String userT = userService.getToken(fetched2);

        assertNotNull(nullT);
        assertNotNull(userT);
        assertNotSame(nullT, userT);

        System.out.println("t: " + nullT);
        System.out.println("u: " + userT);

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
