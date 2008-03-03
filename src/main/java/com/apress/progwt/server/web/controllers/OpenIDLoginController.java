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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.ui.openid.OpenIDAuthenticationProcessingFilter;
import org.springframework.security.ui.openid.OpenIDConsumer;
import org.springframework.security.ui.openid.OpenIDConsumerException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class OpenIDLoginController extends AbstractController {
    private static final Logger log = Logger
            .getLogger(OpenIDLoginController.class);

    private OpenIDConsumer consumer;
    private OpenIDAuthenticationProcessingFilter openIDFilter;

    private String identityField = "openid_url";

    private String trustRoot;

    @Required
    public void setOpenIDFilter(
            OpenIDAuthenticationProcessingFilter openIDFilter) {
        this.openIDFilter = openIDFilter;
    }

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

        // send the user the redirect url to proceed with OpenID
        // authentication
        try {
            String returnToURL = trustRoot
                    + openIDFilter.getFilterProcessesUrl();

            log.debug("ReturnToURL to: " + returnToURL);

            String redirect = consumer.beginConsumption(req, openID,
                    returnToURL);
            log.debug("Redirecting to: " + redirect);

            return new ModelAndView("redirect:" + redirect);

        } catch (OpenIDConsumerException oice) {
            log.error("Consumer error!" + oice + " " + oice.getMessage());

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("message", oice.getMessage());
            if (oice.getCause() != null) {
                model.put("login_error", "Cause: "
                        + oice.getCause().getMessage()
                        + " Couldn't communicate with OpenID server for "
                        + openID);
            } else {
                model.put("login_error",
                        "Are you sure you have an OpenID account?");
            }

            return new ModelAndView(openIDFilter
                    .getAuthenticationFailureUrl(), model);
        }

    }

}
