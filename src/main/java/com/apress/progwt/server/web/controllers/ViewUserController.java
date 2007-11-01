package com.apress.progwt.server.web.controllers;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.ModelAndView;

import com.apress.progwt.client.domain.SchoolAndAppProcess;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.service.SchoolService;

public class ViewUserController extends BasicController {

    private SchoolService schoolService;

    private String notFoundView;

    private static final Logger log = Logger
            .getLogger(ViewUserController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req,
            HttpServletResponse arg1) throws Exception {

        log.debug("SERVLET PATH: " + req.getServletPath() + " "
                + req.getPathInfo() + " " + req.getQueryString());

        Map<String, Object> model = getDefaultModel(req);

        String path = req.getPathInfo();

        String[] pathParts = path.split("/");
        log.debug("!path parts " + Arrays.toString(pathParts));

        // "/user/jeff" splits to [,user,jeff]
        if (pathParts.length < 3) {
            return new ModelAndView(getNotFoundView());
        }

        String nickname = pathParts[2];

        User user = userService.getUserByNicknameFullFetch(nickname);

        log.debug("user u: " + user);
        log.debug("isinit user " + Hibernate.isInitialized(user));
        log.debug("isinit schools "
                + Hibernate.isInitialized(user.getSchoolRankings()));
        for (SchoolAndAppProcess sap : user.getSchoolRankings()) {
            log.debug("isinit sap " + Hibernate.isInitialized(sap));
        }

        if (user == null) {
            return new ModelAndView(getNotFoundView(), "message",
                    "Couldn't find user with nickname: " + nickname);
        }
        model.put("user", user);

        ModelAndView mav = getMav();
        mav.addAllObjects(model);
        return mav;
    }

    @Required
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public String getNotFoundView() {
        return notFoundView;
    }

    @Required
    public void setNotFoundView(String notFoundView) {
        this.notFoundView = notFoundView;
    }

}
