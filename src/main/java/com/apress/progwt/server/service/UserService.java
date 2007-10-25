package com.apress.progwt.server.service;

import java.util.Date;
import java.util.List;

import org.acegisecurity.userdetails.UsernameNotFoundException;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.exception.BusinessException;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.server.web.domain.CreateUserRequestCommand;

public interface UserService {

    void addInvitationsTo(User inviter, int num);

    boolean couldBeOpenID(String openIDusername);

    User createUser(String user, String pass, String email, boolean superV)
            throws BusinessException;

    User createUser(String user, String pass, String email,
            boolean superV, Date dateCreated) throws BusinessException;

    void delete(Integer id) throws PermissionDeniedException;

    boolean exists(String username);

    List<User> getAllUsers();

    User getCurrentUser() throws UsernameNotFoundException;

    User getUserWithNormalization(String username);

    User getUserByNickname(String nickname);

    boolean nowAcceptingSignups();

    void toggleEnabled(Integer id) throws PermissionDeniedException;

    void toggleSupervisor(Integer id) throws PermissionDeniedException;

    User createUser(CreateUserRequestCommand comm) throws SiteException;

    List<User> getTopUsers();

}
