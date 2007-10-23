package com.apress.progwt.server.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.ui.openid.OpenIDConsumer;
import org.acegisecurity.ui.openid.OpenIDConsumerException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class OpenIDLoginController extends AbstractController {
    private static final Logger log = Logger
            .getLogger(OpenIDLoginController.class);

    private OpenIDConsumer consumer;

    private static final String passwordField = "j_password";
    private String identityField = "openid_url";
    private String formLoginUrl = "/j_acegi_security_check";
    private String errorPage = "acegilogin";

    private String trustRoot;
    private String returnTo = "j_acegi_openid_security_check";

    @Required
    public void setTrustRoot(String trustRoot) {
        this.trustRoot = trustRoot;
    }

    public void setConsumer(OpenIDConsumer consumer) {
        this.consumer = consumer;
    }

    public void setIdentityField(String identityField) {
        this.identityField = identityField;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req,
            HttpServletResponse res) throws Exception {

        // get the submitted id field
        String openID = req.getParameter(identityField);

        // TODO: pattern matching
        String password = req.getParameter(passwordField);

        if ((password != null) && (password.length() > 0)) {
            log
                    .debug("Attempting to authenticate using username/password "
                            + formLoginUrl);
            log
                    .debug("Attempting to authenticate using username/password ");

            // forward to authenticationProcessingFilter
            // (/j_acegi_security_check - depends on param names)
            return new ModelAndView(formLoginUrl);

        } else {
            log.info("No pass, doing  OPENID " + openID);
            // send the user the redirect url to proceed with OpenID
            // authentication
            try {
                String returnToURL = trustRoot + returnTo;

                log.debug("ReturnToURL to: " + returnToURL);

                String redirect = consumer.beginConsumption(req, openID,
                        returnToURL);
                log.debug("Redirecting to: " + redirect);

                return new ModelAndView("redirect:" + redirect);

            } catch (OpenIDConsumerException oice) {
                log.error("Consumer error!" + oice + " "
                        + oice.getMessage());

                Map<String, Object> model = new HashMap<String, Object>();
                model.put("message", oice.getMessage());
                if (oice.getCause() != null) {
                    model
                            .put(
                                    "login_error",
                                    "Cause: "
                                            + oice.getCause()
                                                    .getMessage()
                                            + " Couldn't communicate with OpenID server for "
                                            + openID);
                } else {
                    model.put("login_error",
                            "Are you sure you have an OpenID account?");
                }

                return new ModelAndView(errorPage, model);
            }
        }

    }

}
