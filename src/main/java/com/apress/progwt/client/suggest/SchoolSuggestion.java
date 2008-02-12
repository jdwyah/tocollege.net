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
