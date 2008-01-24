package com.apress.progwt.server.web.domain;

public class SearchCommand {

    public String searchString;

    public SearchCommand() {
    }

    public SearchCommand(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

}
