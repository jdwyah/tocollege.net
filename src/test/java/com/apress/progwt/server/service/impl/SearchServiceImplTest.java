package com.apress.progwt.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.server.service.SearchService;
import com.apress.progwt.server.service.UserService;

public class SearchServiceImplTest extends
        AbstractServiceTestWithTransaction {

    private static final Logger log = Logger
            .getLogger(SearchServiceImplTest.class);

    private SearchService searchService;
    private UserService userService;

    public void testSearchSchools() {

        List<School> res = null;

        String searchStr = "";

        searchStr = "";
        res = searchService.searchForSchool(searchStr);
        assertEquals(0, res.size());
        System.out.println(searchStr + " Found: " + res.size());
        for (School school : res) {
            System.out.println("found " + school);
        }
        searchStr = "Dartmou Coll";
        res = searchService.searchForSchool(searchStr);
        assertEquals(1, res.size());
        System.out.println(searchStr + " Found: " + res.size());
        for (School school : res) {
            System.out.println("found " + school);
        }

        searchStr = "D";
        res = searchService.searchForSchool(searchStr);
        assertEquals(10, res.size());
        System.out.println(searchStr + " Found: " + res.size());
        for (School school : res) {
            System.out.println("found " + school);
        }

        searchStr = "Pen State";
        res = searchService.searchForSchool(searchStr);
        assertEquals(10, res.size());
        System.out.println(searchStr + " Found: " + res.size());
        for (School school : res) {
            System.out.println("found " + school);
        }

        searchStr = "Cal Ber";
        res = searchService.searchForSchool(searchStr);
        System.out.println(searchStr + " Found: " + res.size());
        assertEquals(2, res.size());
        for (School school : res) {
            System.out.println("found " + school);
        }

    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
