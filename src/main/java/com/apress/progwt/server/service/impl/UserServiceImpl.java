package com.apress.progwt.server.service.impl;

import java.util.Date;
import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.dao.SaltSource;
import org.acegisecurity.providers.dao.UserCache;
import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.exception.BusinessException;
import com.apress.progwt.server.dao.UserDAO;
import com.apress.progwt.server.domain.ServerSideUser;
import com.apress.progwt.server.service.PermissionDeniedException;
import com.apress.progwt.server.service.UserService;

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

    private int maxUsers;

    private MessageSource messageSource;

    private PasswordEncoder passwordEncoder;
    private SaltSource saltSource;

    private int startingInvitations;

    private UserCache userCache;

    private UserDAO userDAO;

    /**
     * don't let it go negative
     */
    public void addInvitationsTo(User inviter, int num) {
        int current = inviter.getInvitations();
        int newV = current + num;
        if (newV >= 0) {
            inviter.setInvitations(newV);
        }
        userDAO.save(inviter);

        userCache.removeUserFromCache(inviter.getUsername());
    }

    public boolean couldBeOpenID(String username) {
        return username.contains(".") || username.contains("=");
    }

    private User createUser(String username, String userpass, String email)
            throws BusinessException {
        return createUser(username, userpass, email, false);
    }

    /**
     * lowercase usernames before creation
     * 
     * @return
     * @throws HippoBusinessException
     */
    public User createUser(String username, String userpass,
            String email, boolean superV) throws BusinessException {
        return createUser(username, userpass, email, superV, new Date());
    }

    public User createUser(String username, String userpass,
            String email, boolean superV, Date dateCreated)
            throws BusinessException {

        // hmm a bit odd having the logic catch in the
        //
        if (log.isDebugEnabled()) {
            log.debug("u: " + username + " p " + userpass);
        }

        User user = new User();
        user.setUsername(username.toLowerCase());
        user.setEmail(email);
        user.setSupervisor(superV);
        user.setEnabled(true);
        user.setInvitations(startingInvitations);
        user.setDateCreated(dateCreated);

        user = userDAO.save(user);

        if (userpass != null) {

            Object salt = saltSource.getSalt(new ServerSideUser(user));

            user.setPassword(passwordEncoder.encodePassword(userpass,
                    salt));

        }

        User createdU = userDAO.save(user);

        // important. otherwise we were getting directed to the user page
        // in a logged in, but not
        // authenticated state, despite our redirect:/site/index.html
        SecurityContextHolder.getContext().setAuthentication(null);
        return createdU;
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

    public List<User> getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        // if(log.isDebugEnabled()){
        // for (User user : users) {
        // log.info(user.getUsername()+" "+user.isSupervisor());
        // }
        // }
        return users;
    }

    public User getCurrentUser() throws UsernameNotFoundException {

        log.debug("getCurrentUser");

        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        if (null == auth) {
            throw new UsernameNotFoundException("No Authorizations");
        }

        Object obj = auth.getPrincipal();
        String username = "";

        if (obj instanceof UserDetails) {
            log.debug("instance of UserDetails");
            username = ((UserDetails) obj).getUsername();
        } else {
            log.debug("not a UserDetail, it's a "
                    + obj.getClass().getName());
            username = obj.toString();
        }

        if (username.equals(ANONYMOUS)) {
            log.debug("Anonymous return null user");
            return null;
        }

        log.debug("loadUserByUsername " + username);

        try {

            ServerSideUser serverUser = (ServerSideUser) userCache
                    .getUserFromCache(username);

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

    /**
     * only openID users are allowed '.' || '=' and all openID usernames
     * must have a '.' || '=' so, if it's got a '.' || '='
     * janrain.normalize() before the lookup
     */
    public User getUserWithNormalization(String username)
            throws UsernameNotFoundException {

        if (couldBeOpenID(username)) {
            return userDAO.getUserByUsername(com.janrain.openid.Util
                    .normalizeUrl(username));
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
    public void setStartingInvitations(int startingInvitations) {
        this.startingInvitations = startingInvitations;
    }

    @Required
    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    @Required
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
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
            userDAO.save(user);
            userCache.removeUserFromCache(user.getUsername());
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
            userDAO.save(user);
            userCache.removeUserFromCache(user.getUsername());
        } else {
            throw new PermissionDeniedException(
                    "You don't have rights to do that.");
        }
    }

}
