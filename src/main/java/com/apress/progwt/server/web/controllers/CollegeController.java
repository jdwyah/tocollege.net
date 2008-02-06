package com.apress.progwt.server.web.controllers;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apress.progwt.client.domain.GWTSerializer;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.dto.ForumBootstrap;
import com.apress.progwt.client.domain.dto.PostsList;
import com.apress.progwt.client.exception.InfrastructureException;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.UserService;

@Controller
@RequestMapping("/college/*")
public class CollegeController {

    @Autowired
    @Qualifier(value = "GWTSchoolService")
    private GWTSerializer serializer;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private UserService userService;

    private String notFoundView = "redirect:/site/search.html";
    private String view = "college";

    private static final Logger log = Logger
            .getLogger(CollegeController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String collegeHandler(HttpServletRequest req, ModelMap model)
            throws InfrastructureException {

        log.debug("SERVLET PATH: " + req.getServletPath() + " "
                + req.getPathInfo() + " " + req.getQueryString());

        String path = req.getPathInfo();
        path = path.replace('_', ' ');

        String[] pathParts = path.split("/");
        log.debug("!path parts " + Arrays.toString(pathParts));

        // "/college/dartmouth" splits to [,college,dartmouth]
        if (pathParts.length < 3) {
            return getNotFoundView();
        }

        String schoolName = pathParts[2];

        School school = schoolService.getSchoolDetails(schoolName);
        if (school == null) {
            model.addAttribute("message", "Couldn't find school "
                    + schoolName);
            return getNotFoundView();
        }

        PostsList forumPosts = schoolService.getForum(school, 0, 10);
        ForumBootstrap forumBootstrap = new ForumBootstrap(serializer,
                forumPosts, school);
        model.addAttribute("forumBootstrap", forumBootstrap);

        model.addAttribute("school", school);
        model.addAttribute("interestedIn", schoolService
                .getUsersInterestedIn(school));

        ControllerUtil
                .updateModelMapWithDefaults(model, req, userService);

        return view;
    }

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public void setSerializer(GWTSerializer serializer) {
        this.serializer = serializer;
    }

    public String getNotFoundView() {
        return notFoundView;
    }

    public void setNotFoundView(String notFoundView) {
        this.notFoundView = notFoundView;
    }

}
