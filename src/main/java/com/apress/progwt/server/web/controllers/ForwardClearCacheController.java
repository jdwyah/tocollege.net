package com.apress.progwt.server.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

public class ForwardClearCacheController extends BasicController {

    private static final Logger log = Logger
            .getLogger(ForwardClearCacheController.class);

    protected ModelAndView handleRequestInternal(HttpServletRequest req,
            HttpServletResponse arg1) throws Exception {

        // log.debug("SERVLET PATH: "+req.getServletPath()+"
        // "+req.getPathInfo()+"
        // "+req.getQueryString());

        ModelAndView mav = getMav();
        mav.addAllObjects(getDefaultModel(req));
        return mav;
    }

}
