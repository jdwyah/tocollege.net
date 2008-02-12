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
package com.apress.progwt.server.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.domain.ServerSideUser;

public interface UserDAO {

    void delete(User user);

    List<User> getAllUsers(int max);

    List<User> getAllUsers();

    User getForPaypalID(String paypalID);

    User getUserByUsername(String username)
            throws UsernameNotFoundException;

    User getUserForId(long id);

    ServerSideUser loadUserByUsername(final String username)
            throws UsernameNotFoundException, DataAccessException;

    User save(User user);

    long getUserCount();

    User getUserByUsernameFetchAll(String username);

    User getUserByNicknameFetchAll(String nickname);

}
