package com.apress.progwt.server.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apress.progwt.client.domain.dto.SearchResult;
import com.apress.progwt.client.exception.SiteException;
import com.apress.progwt.server.service.SearchService;
import com.apress.progwt.server.service.UserService;
import com.apress.progwt.server.web.domain.SearchCommand;

@Controller
@RequestMapping("/search.html")
public class SearchController {
    private static final Logger log = Logger
            .getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelMap searchHandler(HttpServletRequest req,
            @RequestParam(value = "searchString", required = false)
            String searchString,
            @RequestParam(value = "message", required = false)
            String message) throws SiteException {

        ModelMap rtn = ControllerUtil.getModelMap(req, userService);

        rtn.addAttribute("message", message);
        addResults(rtn, new SearchCommand(searchString));

        return rtn;
    }

    private void addResults(ModelMap rtn, SearchCommand command)
            throws SiteException {

        SearchResult result = searchService.search(command
                .getSearchString());

        System.out.println("got search " + result);

        rtn.addAttribute("searchResult", result);
        rtn.addAttribute("command", command);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelMap searchHandler(HttpServletRequest req,
            @ModelAttribute("command")
            SearchCommand command) throws SiteException {

        ModelMap myModel = ControllerUtil.getModelMap(req, userService);

        addResults(myModel, command);

        return myModel;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
