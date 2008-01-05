package com.apress.progwt.server.web.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.apress.progwt.server.service.UserService;

/**
 * abstract controller suitable for extension when you'd like to include
 * the currentUser and the ie7 flag.
 * 
 * @author Jeff Dwyer
 * 
 */
public abstract class BasicController extends AbstractController {
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
        return ControllerUtil.getDefaultModel(req, userService);
    }

    public void setView(String view) {
        this.view = view;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
