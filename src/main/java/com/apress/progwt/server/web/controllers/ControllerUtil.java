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

import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.ui.ModelMap;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.service.UserService;

/**
 * useful functionality for controllers.
 * 
 * @author Jeff Dwyer
 * 
 */
public class ControllerUtil {

    /**
     * 
     * @param req
     * @param userService
     * @return
     */
    public static Map<String, Object> getDefaultModel(
            HttpServletRequest req, UserService userService) {
        Map<String, Object> model = new HashMap<String, Object>();

        User su = null;
        model.put("message", req.getParameter("message"));
        try {
            su = userService.getCurrentUser();
            model.put("user", su);
        } catch (UsernameNotFoundException e) {
            // log.debug("No user logged in.");
        }

        if (req != null) {
            // IE < 7 check. used in common.ftl PNGImage
            String userAgent = req.getHeader("User-Agent");
            if (userAgent != null && userAgent.contains("MSIE")
                    && !userAgent.contains("MSIE 7")) {
                model.put("iePre7", true);
            }
        }

        return model;
    }

    public static ModelMap getModelMap(HttpServletRequest req,
            UserService userService) {
        ModelMap rtn = new ModelMap();
        rtn.addAllAttributes(getDefaultModel(req, userService));
        return rtn;
    }

    public static void updateModelMapWithDefaults(ModelMap map,
            HttpServletRequest req, UserService userService) {
        map.addAllAttributes(getDefaultModel(req, userService));
    }
}
