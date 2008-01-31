package com.apress.progwt.client.suggest;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.domain.ProcessType;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class ProcessSuggestion implements Suggestion {
    private ProcessType value;
    private String query;

    public ProcessSuggestion(ProcessType processType, String query) {
        this.value = processType;
        this.query = query;
    }

    public ProcessSuggestion() {
    }

    public String getDisplayString() {
        return AbstractSuggestOracle.highlight(value.getName(), query);
    }

    public String getReplacementString() {
        Log.debug("get replacementString " + value);
        return value.getName();
    }

    public String getQuery() {
        return query;
    }

    public ProcessType getValue() {
        return value;
    }

}