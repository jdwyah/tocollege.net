package com.apress.progwt.server.service.gwt;

import org.apache.log4j.Logger;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.dto.UserAndToken;
import com.apress.progwt.client.exception.BusinessException;
import com.apress.progwt.client.service.remote.GWTUserService;
import com.apress.progwt.server.gwt.GWTController;
import com.apress.progwt.server.service.UserService;

public class GWTUserServiceImpl extends GWTController
        implements GWTUserService {
    private static final Logger log = Logger
            .getLogger(GWTUserServiceImpl.class);

    private UserService userService;

    public UserAndToken getCurrentUser() throws BusinessException {

        try {

            UserAndToken rtn = userService.getCurrentUserAndToken();

            if (rtn.getUser() != null) {
                log.info("GWT get current user... "
                        + rtn.getUser().getUsername());
                // System.out.println("\n\n\n---------------");
                // System.out.println("user school rankings: "
                // + user.getSchoolRankings().size());
                // System.out.println("user process types: "
                // + user.getProcessTypes().size());
                User fetched = userService.getUserByNicknameFullFetch(rtn
                        .getUser().getNickname());
                System.out.println("\n\n---------------");
                System.out.println("fetched school rankings: "
                        + fetched.getSchoolRankings().size());
                System.out.println("fetched process types: "
                        + fetched.getProcessTypes().size());

                rtn.setUser(fetched);
            }
            return rtn;

        } catch (UsernameNotFoundException u) {
            throw new BusinessException(u.getMessage());
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
