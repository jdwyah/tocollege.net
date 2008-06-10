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

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apress.progwt.client.exception.InfrastructureException;

@Controller
@RequestMapping("/login.html")
public class LoginController {
    private static Logger log = Logger.getLogger(LoginController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelMap loginHandler(HttpServletRequest req,
            @RequestParam(value = "access_error", required = false)
            String access_error,
            @RequestParam(value = "login_error", required = false)
            String login_error, ModelMap map)
            throws InfrastructureException {

        AuthenticationException authExcept = (AuthenticationException) req
                .getSession()
                .getAttribute(
                        AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);

        // parameter may be on param line if we're redirect:ed here
        // (createUserController)
        map.addAttribute("message", req.getParameter("message"));
        
        if (authExcept != null) {
            String message = authExcept.getMessage();
            log.info("Login Error " + message + " uname: "
                    + req.getParameter("j_username"));
            map.addAttribute("login_error", message);
        }
        if (access_error != null) {
            map.addAttribute("login_error", "Access Denied");
        }

        return map;
    }
}
