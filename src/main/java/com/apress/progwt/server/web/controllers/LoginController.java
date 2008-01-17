package com.apress.progwt.server.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class LoginController extends AbstractController {
    private static Logger log = Logger.getLogger(LoginController.class);

    private String view;

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req,
            HttpServletResponse arg1) throws Exception {
        if (req.getParameter("login_error") != null) {
            String message = ((AuthenticationException) req
                    .getSession()
                    .getAttribute(
                            AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY))
                    .getMessage();
            log.info("Login Error " + message + " uname: "
                    + req.getParameter("j_username"));
            return new ModelAndView(getView(), "login_error", message);
        }
        return new ModelAndView(getView());
    }

}
