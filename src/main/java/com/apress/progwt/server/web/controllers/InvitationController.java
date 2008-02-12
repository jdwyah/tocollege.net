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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.service.InvitationService;

/**
 * TODO Cheating! Lazy programmer didn't feel like doing a
 * SimpleFormController, so just reading request.
 * 
 * @author Jeff Dwyer
 * 
 */
public class InvitationController extends BasicController {
    private static final Logger log = Logger
            .getLogger(InvitationController.class);

    private InvitationService invitationService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req,
            HttpServletResponse arg1) throws Exception {

        String email = req.getParameter("email");

        Map model = getDefaultModel(req);

        User u = (User) model.get("user");
        invitationService.createAndSendInvitation(email, u);

        // userService.getCurrentUser()

        model
                .put(
                        "message",
                        "We've sent "
                                + email
                                + " an invitation.<br> Thanks for helping to spread the word.");

        ModelAndView mav = new ModelAndView();
        mav.addAllObjects(model);
        return mav;
    }

    public void setInvitationService(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

}
