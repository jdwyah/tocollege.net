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

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.UserService;

/**
 * Even with the Spring 2.5 enhancements in convention over configuration,
 * using XML each page still requires a bean definition and a
 * corresponding (if empty) class that extends Controller. Using
 * annotations we can map many URLs is this one class easily.
 * 
 * See how we can map requests here. We can get almost anything we desire
 * from the request to streams, to model and binding errors just by asking
 * in the method signature.
 * http://static.springframework.org/spring/docs/2.5.x/reference/mvc.html#mvc-ann-requestmapping
 * 
 * @author Jeff Dwyer
 * 
 */
@Controller
public class SimpleAnnotatedController {
    private static final Logger log = Logger
            .getLogger(SimpleAnnotatedController.class);

    private UserService userService;
    private SchoolService schoolService;

    @RequestMapping("/acknowledgements.html")
    public ModelMap acknowledgementsHandler(HttpServletRequest req) {
        return ControllerUtil.getModelMap(req, userService);
    }

    @RequestMapping("/calculator.html")
    public ModelMap calculatorHandler(HttpServletRequest req) {
        return ControllerUtil.getModelMap(req, userService);
    }

    @RequestMapping("/terms.html")
    public ModelMap termsHandler(HttpServletRequest req) {
        return ControllerUtil.getModelMap(req, userService);
    }

    @RequestMapping("/contact.html")
    public ModelMap contactHandler(HttpServletRequest req) {
        return ControllerUtil.getModelMap(req, userService);
    }

    @RequestMapping("/about.html")
    public ModelMap aboutHandler(HttpServletRequest req) {
        return ControllerUtil.getModelMap(req, userService);

    }
    @RequestMapping("/congratulations.html")
    public ModelMap congratulationsHandler(HttpServletRequest req) {
        return ControllerUtil.getModelMap(req, userService);

    }

    @RequestMapping("/users.html")
    public ModelMap usersHandler(HttpServletRequest req) {
        ModelMap rtn = ControllerUtil.getModelMap(req, userService);

        rtn.addAttribute("topUsers", userService.getTopUsers(20));

        return rtn;
    }

    /**
     * this is the gwtLoginTargetURL. When GWT does a form based login, it
     * will redirect here. GWT can then read this 'ok' in to know that it
     * was ok.
     * 
     * @param w
     * @throws IOException
     */
    @RequestMapping("/secure/gwtLoginOK.html")
    public void gwtLoginHandler(Writer w) throws IOException {
        w.write("OK");
        w.close();
    }

    /**
     * 
     * @param req
     * @param startLetter -
     *            if unavailable, show 'topschools'
     * @param start -
     *            paging start
     * @return
     */
    @RequestMapping("/schools.html")
    public ModelMap schoolsHandler(HttpServletRequest req,
            @RequestParam(value = "startLetter", required = false)
            String startLetter,
            @RequestParam(value = "start", required = false)
            Integer start) {
        ModelMap rtn = ControllerUtil.getModelMap(req, userService);

        if (start == null) {
            start = 0;
        }

        List<School> schools = null;
        if (startLetter == null) {
            startLetter = "";
            schools = schoolService.getTopSchools(start, 20);
        } else {
            schools = schoolService.getSchoolsStarting(startLetter,
                    start, 20);
        }

        rtn.addAttribute("start", start);
        rtn.addAttribute("startLetter", startLetter);
        rtn.addAttribute("schools", schools);

        return rtn;
    }

    /**
     * No bean definition in dispatcher-servlet.xml since we'll be found
     * with the scan for (AT)Controller because of <context:component-scan
     * base-package="com.apress.progwt.server.web.controllers" />
     * 
     * @param userService
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

}
