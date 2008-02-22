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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.apress.progwt.client.exception.InfrastructureException;
import com.apress.progwt.server.domain.MailingListEntry;
import com.apress.progwt.server.service.InvitationService;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.SearchService;
import com.apress.progwt.server.service.UserService;

/**
 * 
 * @author Jeff Dwyer
 * 
 */
@Controller
public class SupervisorController {
    private static final Logger log = Logger
            .getLogger(SupervisorController.class);

    private UserService userService;
    private SchoolService schoolService;

    private InvitationService invitationService;
    private SearchService searchService;

    @RequestMapping("/secure/extreme/mailinglist.html")
    public ModelAndView mailingListHandler(HttpServletRequest req,
            ModelMap map) {
        ControllerUtil.updateModelMapWithDefaults(map, req, userService);

        map.addAttribute("list", invitationService.getMailingList());
        return new ModelAndView("secure/extreme/mailinglist", map);

    }

    @RequestMapping("/secure/extreme/mailinglistaction.html")
    public ModelAndView resendInvite(HttpServletRequest request,
            @RequestParam("entryID")
            long entryID, ModelMap map) throws InfrastructureException {

        MailingListEntry entry = invitationService.getEntryById(entryID);

        invitationService.sendInvite(entry);

        map.addAttribute("message", "Success Re-Sending Invite");

        return mailingListHandler(request, map);
    }

    @RequestMapping("/secure/extreme/scripts.html")
    public ModelAndView scriptsHandler(HttpServletRequest req,
            ModelMap map) {
        ControllerUtil.updateModelMapWithDefaults(map, req, userService);

        return new ModelAndView("secure/extreme/scripts", map);
    }

    @RequestMapping("/secure/extreme/scripts_action_search_index.html")
    public ModelAndView scriptsActionHandler(HttpServletRequest req,
            ModelMap map) {

        searchService.reindex();
        map.addAttribute("message", "Re-Indexing begun");

        return scriptsHandler(req, map);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @Autowired
    public void setInvitationService(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

}
