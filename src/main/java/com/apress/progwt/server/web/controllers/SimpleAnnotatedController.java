package com.apress.progwt.server.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/about.html")
    public ModelMap aboutHandler(HttpServletRequest req) {
        return ControllerUtil.getModelMap(req, userService);

    }

    @RequestMapping("/users.html")
    public ModelMap usersHandler(HttpServletRequest req) {
        ModelMap rtn = ControllerUtil.getModelMap(req, userService);

        rtn.addAttribute("topUsers", userService.getTopUsers(20));

        return rtn;
    }

    @RequestMapping("/schools.html")
    public ModelMap schoolsHandler(HttpServletRequest req) {
        ModelMap rtn = ControllerUtil.getModelMap(req, userService);

        rtn
                .addAttribute("topSchools", schoolService.getTopSchools(
                        0, 20));

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
