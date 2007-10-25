package com.apress.progwt.server.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.service.UserService;

public class BasicController extends AbstractController {
    private static final Logger log = Logger
            .getLogger(BasicController.class);

    protected UserService userService;
    private String view;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req,
            HttpServletResponse arg1) throws Exception {

        // log.debug("SERVLET PATH: "+req.getServletPath()+"
        // "+req.getPathInfo()+"
        // "+req.getQueryString());

        ModelAndView mav = getMav();
        mav.addAllObjects(getDefaultModel(req));
        return mav;
    }

    protected ModelAndView getMav() {
        ModelAndView mav;
        if (view == null) {
            mav = new ModelAndView();
        } else {
            mav = new ModelAndView(view);
        }
        return mav;
    }

    protected Map<String, Object> getDefaultModel(HttpServletRequest req) {
        return getDefaultModel(req, userService);
    }

    public static Map<String, Object> getDefaultModel(
            HttpServletRequest req, UserService userService) {
        Map<String, Object> model = new HashMap<String, Object>();

        User su = null;
        try {
            su = userService.getCurrentUser();
            model.put("user", su);
        } catch (UsernameNotFoundException e) {
            // log.debug("No user logged in.");
        }

        // IE < 7 check. used in common.ftl PNGImage
        String userAgent = req.getHeader("User-Agent");
        if (userAgent.contains("MSIE") && !userAgent.contains("MSIE 7")) {
            model.put("iePre7", true);
        }

        return model;
    }

    public void setView(String view) {
        this.view = view;
    }

    @Required
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
