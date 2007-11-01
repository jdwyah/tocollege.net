package com.apress.progwt.server.web.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.ModelAndView;

import com.apress.progwt.server.domain.FrontPageData;
import com.apress.progwt.server.service.SchoolService;

public class IndexController extends BasicController {

    private SchoolService schoolService;
    private static final Logger log = Logger
            .getLogger(IndexController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req,
            HttpServletResponse arg1) throws Exception {

        log.debug("SERVLET PATH: " + req.getServletPath() + " "
                + req.getPathInfo() + " " + req.getQueryString());

        Map<String, Object> model = getDefaultModel(req);

        // parameter may be on param line if we're redirect:ed here
        // (createUserController)
        model.put("message", req.getParameter("message"));

        model.put("frontPage", new FrontPageData(userService,
                schoolService));

        ModelAndView mav = new ModelAndView();
        mav.addAllObjects(model);
        return mav;
    }

    @Required
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

}
