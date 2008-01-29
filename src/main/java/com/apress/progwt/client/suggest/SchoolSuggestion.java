package com.apress.progwt.client.suggest;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class SchoolSuggestion implements Suggestion {
    private String schoolName;
    private String query;

    public SchoolSuggestion(String schoolName, String query) {
        this.schoolName = schoolName;
        this.query = query;
    }

    public SchoolSuggestion() {
    }

    public String getDisplayString() {
        return AbstractSuggestOracle.highlight(schoolName, query);
    }

    public String getReplacementString() {
        return schoolName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

}
