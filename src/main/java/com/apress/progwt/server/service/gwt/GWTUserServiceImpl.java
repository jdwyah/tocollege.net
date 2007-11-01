package com.apress.progwt.server.service.gwt;

import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.log4j.Logger;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.exception.BusinessException;
import com.apress.progwt.client.service.remote.GWTUserService;
import com.apress.progwt.server.gwt.GWTSpringControllerReplacement;
import com.apress.progwt.server.service.UserService;

public class GWTUserServiceImpl extends GWTSpringControllerReplacement
        implements GWTUserService {
    private static final Logger log = Logger
            .getLogger(GWTUserServiceImpl.class);

    private UserService userService;

    public User getCurrentUser() throws BusinessException {

        try {
            User user = userService.getCurrentUser();
            if (user != null) {
                log.info("GWT get current user... " + user.getUsername());
            }
            return userService.getUserByNicknameFullFetch(user
                    .getNickname());
        } catch (UsernameNotFoundException u) {
            throw new BusinessException(u.getMessage());
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
