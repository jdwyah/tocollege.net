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
package com.apress.progwt.server.web.controllers;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.ModelAndView;

import com.apress.progwt.client.domain.Application;
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

        User fetchedUser = userService
                .getUserByNicknameFullFetch(nickname);

        log.debug("user u: " + fetchedUser);
        log.debug("isinit user " + Hibernate.isInitialized(fetchedUser));
        log.debug("isinit schools "
                + Hibernate
                        .isInitialized(fetchedUser.getSchoolRankings()));
        for (Application sap : fetchedUser.getSchoolRankings()) {
            log.debug("isinit sap " + Hibernate.isInitialized(sap));
        }

        if (fetchedUser == null) {
            return new ModelAndView(getNotFoundView(), "message",
                    "Couldn't find user with nickname: " + nickname);
        }
        model.put("viewUser", fetchedUser);

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
