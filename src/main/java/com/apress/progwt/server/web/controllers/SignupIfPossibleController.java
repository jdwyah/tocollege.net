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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.apress.progwt.server.service.InvitationService;
import com.apress.progwt.server.service.UserService;
import com.apress.progwt.server.util.CryptUtils;
import com.apress.progwt.server.web.domain.CreateUserRequestCommand;

public class SignupIfPossibleController extends AbstractController {

    private static final Logger log = Logger
            .getLogger(SignupIfPossibleController.class);

    private InvitationService invitationService;
    private UserService userService;
    private String mailingListView;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req,
            HttpServletResponse arg1) throws Exception {

        if (userService.nowAcceptingSignups()) {
            Map<String, Object> model = new HashMap<String, Object>();
            CreateUserRequestCommand comm = new CreateUserRequestCommand();

            Calendar c = Calendar.getInstance();
            c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
            String secretKey = CryptUtils.hashString(invitationService
                    .getSalt()
                    + c.get(Calendar.DAY_OF_WEEK_IN_MONTH));

            model.put("hideSecretKey", true);
            model.put("secretkey", secretKey);

           
            if (req.getServletPath().contains("2")) {
                return new ModelAndView("redirect:signup2.html", model);
            } else {
                return new ModelAndView("redirect:signup.html", model);
            }
                
        } else {
            return new ModelAndView(getMailingListView());
        }
    }



    public String getMailingListView() {
        return mailingListView;
    }

    @Required
    public void setInvitationService(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @Required
    public void setMailingListView(String mailingListView) {
        this.mailingListView = mailingListView;
    }

    @Required
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
