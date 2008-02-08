package com.apress.progwt.server.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.UrlIdentifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.MessageSource;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.SaltSource;
import org.springframework.security.providers.dao.UserCache;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.RatingType;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.dto.UserAndToken;
import com.apress.progwt.client.exception.BusinessException;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.server.dao.SchoolDAO;
import com.apress.progwt.server.dao.UserDAO;
import com.apress.progwt.server.domain.ServerSideUser;
import com.apress.progwt.server.service.PermissionDeniedException;
import com.apress.progwt.server.service.UserService;
import com.apress.progwt.server.web.domain.CreateUserRequestCommand;

/**
 * 
 * 
 * TODO I feel like we shouldn't have to be updating the UserCache
 * ourselves. Why is this? Right now it's flaky because we need to remove
 * users too.
 * 
 * @author Jeff Dwyer
 * 
 */
@Transactional
public class UserServiceImpl implements UserService {

    /**
     * match with applicationContext-acegi-security.xml <bean
     * id="anonymousProcessingFilter">
     */
    public static final String ANONYMOUS = "anonymousUser";

    private static final long CANCELLED_SUBSCRIPTION_ID = 1;
    private static final Logger log = Logger
            .getLogger(UserServiceImpl.class);

    public static final String SAMPLE_TAG_TITLE = "Sample Movies";

    public static String normalizeUrl(String username)
            throws SiteException {
        if (username == null) {
            throw new RuntimeException("Invalid openID: " + username);
        }
        try {
            String rtn;
            // http || https
            if (username.startsWith("http")) {
                rtn = UrlIdentifier.normalize(username).toExternalForm();
            } else {
                rtn = UrlIdentifier.normalize("http://" + username)
                        .toExternalForm();
            }
            System.out.println("rtn |" + rtn + "|");
            if (rtn.equals("http://")) {
                throw new DiscoveryException("Invalid openID: "
                        + username);
            } else {
                return rtn;
            }
        } catch (DiscoveryException e) {
            log.error("Invalid OpenID " + username + " " + e);
            throw new SiteException("Invalid openID: " + username);
        }
    }

    private int maxUsers;

    private MessageSource messageSource;
    private PasswordEncoder passwordEncoder;

    private SaltSource saltSource;

    private SchoolDAO schoolDAO;

    private int startingInvitations;
    private String tokenSalt;

    private UserCache userCache;
    private UserDAO userDAO;

    private Cache userTokenCache;

    private AuthenticationManager authMgr;

    /**
     * don't let it go negative
     */
    public void addInvitationsTo(User inviter, int num) {
        int current = inviter.getInvitations();
        int newV = current + num;
        if (newV >= 0) {
            inviter.setInvitations(newV);
        }
        save(inviter);
    }

    public void changePassword(String oldPassword, String newPassword) {

        User user = getCurrentUser();

        Authentication oldAuth = new UsernamePasswordAuthenticationToken(
                user.getUsername(), oldPassword);
        authMgr.authenticate(oldAuth);

        createPassWord(user, newPassword);

        log.debug("password changed, saving");
        save(user);

        log.debug("remove from cache");
        userCache.removeUserFromCache(user.getUsername());

        log.debug("change security context");
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(), newPassword);
        authMgr.authenticate(newAuthentication);
        SecurityContextHolder.getContext().setAuthentication(
                newAuthentication);

    }

    public boolean couldBeOpenID(String username) {
        return username.contains(".") || username.contains("=");
    }

    public User createUser(CreateUserRequestCommand comm)
            throws SiteException {

        // duplicate username as nickname
        if (comm.isStandard()) {
            return createUser(comm.getUsername(), comm.getPassword(),
                    comm.getEmail(), comm.getUsername());
        }
        // use nickname different than openid
        else if (comm.isOpenID()) {
            return createUser(comm.getOpenIDusernameDoNormalization(),
                    null, comm.getEmail(), comm.getOpenIDnickname());
        } else {
            throw new RuntimeException(
                    "Command Neither standard nor open");
        }

    }

    private User createUser(String username, String userpass,
            String email, String nickname) throws BusinessException {
        return createUser(username, userpass, email, false, new Date(),
                nickname);
    }

    public User createUser(String username, String userpass,
            String email, boolean superV) throws BusinessException {
        return createUser(username, userpass, email, superV, new Date(),
                username);
    }

    /**
     * lowercase usernames before creation
     * 
     * @return
     * @throws BusinessException
     */
    public User createUser(String username, String userpass,
            String email, boolean superV, Date dateCreated,
            String nickname) throws BusinessException {

        // hmm a bit odd having the logic catch in the
        //
        if (log.isDebugEnabled()) {
            log.debug("u: " + username + " p " + userpass);
        }

        User user = new User();
        user.setUsername(username.toLowerCase());
        user.setNickname(user.getUsername());
        user.setEmail(email);
        user.setSupervisor(superV);
        user.setEnabled(true);
        user.setInvitations(startingInvitations);
        user.setDateCreated(dateCreated);

        user = save(user);

        if (userpass != null) {
            createPassWord(user, userpass);

        }

        User createdU = save(user);

        createdU = setup(createdU);

        // important. otherwise we were getting directed to the user page
        // in a logged in, but not
        // authenticated state, despite our redirect:/site/index.html
        SecurityContextHolder.getContext().setAuthentication(null);
        return createdU;
    }

    private void createPassWord(User user, String userpass) {
        Object salt = saltSource.getSalt(new ServerSideUser(user));
        user.setPassword(passwordEncoder.encodePassword(userpass, salt));
    }

    public void delete(Integer id) throws PermissionDeniedException {
        if (getCurrentUser().isSupervisor()) {
            User user = userDAO.getUserForId(id);
            userDAO.delete(user);

            userCache.removeUserFromCache(user.getUsername());
        } else {
            throw new PermissionDeniedException(
                    "You don't have rights to do that.");
        }
    }

    /**
     * Return if the command has a unique username
     */
    public boolean exists(String username) {
        try {
            userDAO.loadUserByUsername(username);
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

    public boolean existsNickname(String nickname) {
        try {
            userDAO.getUserByNicknameFetchAll(nickname);
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getCurrentUser() throws UsernameNotFoundException {
        return getCurrentUser(true);
    }

    public User getCurrentUser(boolean useCache)
            throws UsernameNotFoundException {

        log.debug("getCurrentUser");

        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        if (null == auth) {
            throw new UsernameNotFoundException("No Authorizations");
        }

        Object obj = auth.getPrincipal();
        String username = "";

        if (obj instanceof UserDetails) {
            username = ((UserDetails) obj).getUsername();
        } else {
            username = obj.toString();
        }

        if (username.equals(ANONYMOUS)) {
            log.debug("Anonymous return null user");
            return null;
        }

        log.debug("loadUserByUsername " + username);

        try {

            ServerSideUser serverUser = null;
            if (useCache) {
                serverUser = (ServerSideUser) userCache
                        .getUserFromCache(username);
            }

            User u;
            if (serverUser == null) {

                u = userDAO.getUserByUsername(username);
                userCache.putUserInCache(new ServerSideUser(u));

            } else {
                u = serverUser.getUser();
            }

            return u;
        } catch (UsernameNotFoundException e) {
            log.debug(e);
            throw e;
        }

    }

    public UserAndToken getCurrentUserAndToken() {
        User currentUser = getCurrentUser();

        return new UserAndToken(currentUser, getToken(currentUser));
    }

    /**
     * Question, what should we return for user == null? We'll avoid
     * putting it in the cache, and just return another random string.
     * There's no way to get this value again and that's probably what we
     * want.
     */
    public String getToken(User user) {

        if (user == null) {
            return RandomStringUtils.randomAscii(10);
        }
        Element e = userTokenCache.get(user);
        if (e != null) {
            String token = (String) e.getValue();
            log.debug("Found existing token for: " + user + " token: "
                    + token);
            return token;
        } else {

            String token = RandomStringUtils.randomAscii(10);
            log.debug("No existing token for: " + user + " new token: "
                    + token);
            Element newElement = new Element(user, (Serializable) token);
            userTokenCache.put(newElement);
            return token;
        }
    }

    public List<User> getTopUsers(int max) {
        List<User> users = userDAO.getAllUsers(max);
        // if(log.isDebugEnabled()){
        // for (User user : users) {
        // log.info(user.getUsername()+" "+user.isSupervisor());
        // }
        // }
        return users;
    }

    public User getUserByNicknameFullFetch(String nickname) {
        return userDAO.getUserByNicknameFetchAll(nickname);
    }

    /**
     * only openID users are allowed '.' || '=' and all openID usernames
     * must have a '.' || '=' so, if it's got a '.' || '='
     * janrain.normalize() before the lookup
     * 
     * @throws DiscoveryException
     */
    public User getUserWithNormalization(String username)
            throws UsernameNotFoundException, SiteException {

        if (couldBeOpenID(username)) {

            return userDAO.getUserByUsername(normalizeUrl(username));

            // return userDAO.getUserByUsername(com.janrain.openid.Util
            // .normalizeUrl(username));
        } else {
            return userDAO.getUserByUsername(username);
        }
    }

    private String gm(String messageName) {
        return messageSource.getMessage(messageName, null, null);
    }

    public boolean nowAcceptingSignups() {
        return userDAO.getUserCount() < maxUsers;
    }

    public User save(User user) {
        User rtn = userDAO.save(user);
        userCache.removeUserFromCache(rtn.getUsername());
        return rtn;
    }

    @Required
    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    @Required
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Required
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Required
    public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }

    @Required
    public void setSchoolDAO(SchoolDAO schoolDAO) {
        this.schoolDAO = schoolDAO;
    }

    @Required
    public void setStartingInvitations(int startingInvitations) {
        this.startingInvitations = startingInvitations;
    }

    private User setup(User createdU) {

        for (ProcessType processType : schoolDAO.getDefaultProcessTypes()) {
            createdU.getProcessTypes().add(processType);
        }
        for (RatingType ratingType : schoolDAO.getDefaultRatingTypes()) {
            createdU.getRatingTypes().add(ratingType);
        }

        return save(createdU);
    }

    @Required
    public void setAuthMgr(AuthenticationManager authMgr) {
        this.authMgr = authMgr;
    }

    @Required
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    @Required
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Required
    public void setUserTokenCache(Cache userTokenCache) {
        this.userTokenCache = userTokenCache;
    }

    /**
     * TODO LOW AOP this security concern
     */
    public void toggleEnabled(Integer id)
            throws PermissionDeniedException {
        log.info("toggleEnabled " + getCurrentUser().getUsername() + " "
                + getCurrentUser().isSupervisor());
        if (getCurrentUser().isSupervisor()) {
            User user = userDAO.getUserForId(id);
            user.setEnabled(!user.isEnabled());
            save(user);
        } else {
            throw new PermissionDeniedException(
                    "You don't have rights to do that.");
        }
    }

    public void toggleSupervisor(Integer id)
            throws PermissionDeniedException {
        if (getCurrentUser().isSupervisor()) {
            System.out.println("ID " + id);
            User user = userDAO.getUserForId(id);
            user.setSupervisor(!user.isSupervisor());
            save(user);
        } else {
            throw new PermissionDeniedException(
                    "You don't have rights to do that.");
        }
    }

}
