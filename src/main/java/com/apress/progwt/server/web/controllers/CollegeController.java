package com.apress.progwt.server.web.controllers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.ModelAndView;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.service.impl.SchoolServiceImpl;

public class CollegeController extends BasicController {

    private SchoolServiceImpl schoolService;

    private String notFoundView;

    private static final Logger log = Logger
            .getLogger(CollegeController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req,
            HttpServletResponse arg1) throws Exception {

        log.debug("SERVLET PATH: " + req.getServletPath() + " "
                + req.getPathInfo() + " " + req.getQueryString());

        Map<String, Object> model = getDefaultModel(req);

        String path = req.getPathInfo();
        path = path.replace('_', ' ');

        String[] pathParts = path.split("/");
        log.debug("!path parts " + Arrays.toString(pathParts));

        // "/college/dartmouth" splits to [,college,dartmouth]
        if (pathParts.length < 3) {
            return new ModelAndView(getNotFoundView());
        }

        String schoolName = pathParts[2];

        School school = schoolService.getSchoolDetails(schoolName);
        if (school == null) {
            return new ModelAndView(getNotFoundView(), "message",
                    "Couldn't find school " + schoolName);
        }
        model.put("school", school);
        model.put("interestedIn", new LinkedList<User>());

        ModelAndView mav = getMav();
        mav.addAllObjects(model);
        return mav;
    }

    @Required
    public void setSchoolService(SchoolServiceImpl schoolService) {
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
