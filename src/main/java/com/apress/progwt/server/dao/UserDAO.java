package com.apress.progwt.server.dao;

import java.util.List;

import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.domain.ServerSideUser;

public interface UserDAO {

    void delete(User user);

    List<User> getAllUsers();

    User getForPaypalID(String paypalID);

    User getUserByUsername(String username)
            throws UsernameNotFoundException;

    User getUserForId(long id);

    ServerSideUser loadUserByUsername(final String username)
            throws UsernameNotFoundException, DataAccessException;

    User save(User user);

    long getUserCount();

    User getUserByNickname(String nickname);

}
