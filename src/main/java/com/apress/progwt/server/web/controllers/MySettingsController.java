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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.apress.progwt.server.service.UserService;
import com.apress.progwt.server.web.domain.PasswordChangeCommand;
import com.apress.progwt.server.web.domain.validation.PasswordChangeCommandValidator;

@Controller
@RequestMapping("/secure/settings.html")
public class MySettingsController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordChangeCommandValidator validator;

    private static final Logger log = Logger
            .getLogger(MySettingsController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView setupForm(HttpServletRequest req, ModelMap map,
            @RequestParam(value = "message", required = false)
            String message) {
        ControllerUtil
                .updateModelMapWithDefaults(map, req, userService);
        map.addAttribute("message", message);

        map.addAttribute("command", new PasswordChangeCommand());
        return new ModelAndView("secure/settings", map);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("command")
    PasswordChangeCommand command, BindingResult result, ModelMap map) {

        validator.validate(command, result);
        if (result.hasErrors()) {
            return "secure/settings";
        }

        try {
            userService.changePassword(command.getOldPassword(), command
                    .getNewPassword());
        } catch (Exception e) {
            log.warn(e);
            return "secure/settings";
        }

        map.addAttribute("message", "Password Change Success");
        return "redirect:/secure/settings.html";
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setValidator(PasswordChangeCommandValidator validator) {
        this.validator = validator;
    }

}
