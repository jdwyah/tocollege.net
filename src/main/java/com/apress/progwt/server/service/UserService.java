/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.apress.progwt.server.service;

import java.util.Date;
import java.util.List;

import org.springframework.security.userdetails.UsernameNotFoundException;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.dto.UserAndToken;
import com.apress.progwt.client.exception.BusinessException;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.server.web.domain.CreateUserRequestCommand;

public interface UserService {

    void addInvitationsTo(User inviter, int num);

    void changePassword(String oldPassword, String newPassword);

    boolean couldBeOpenID(String openIDusername);

    User createUser(CreateUserRequestCommand comm) throws SiteException;

    User createUser(String user, String pass, String email, boolean superV)
            throws BusinessException;

    User createUser(String user, String pass, String email,
            boolean superV, Date dateCreated, String nickname)
            throws BusinessException;

    void delete(Integer id) throws PermissionDeniedException;

    boolean exists(String username);

    List<User> getAllUsers();

    User getCurrentUser() throws UsernameNotFoundException;

    User getCurrentUser(boolean useUserCache)
            throws UsernameNotFoundException;

    List<User> getTopUsers(int max);

    User getUserByNicknameFullFetch(String nickname);

    User getUserWithNormalization(String username)
            throws UsernameNotFoundException, SiteException;

    boolean nowAcceptingSignups();

    void toggleEnabled(Integer id) throws PermissionDeniedException;

    void toggleSupervisor(Integer id) throws PermissionDeniedException;

    User save(User user);

    UserAndToken getCurrentUserAndToken();

    String getToken(User user);

    boolean existsNickname(String nickname);

}
