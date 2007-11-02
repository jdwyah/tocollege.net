package com.apress.progwt.client.college;

import java.util.ArrayList;
import java.util.List;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.rpc.EZCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SchoolCompleteOracle extends AbstractSuggestOracle {

    protected class SchoolSuggestion implements SuggestionExt {
        private final School value;
        private String query;

        public SchoolSuggestion(School school, String query) {
            this.value = school;
            this.query = query;
        }

        public String getDisplayString() {
            return highlight(value.getName(), query);
        }

        public String getReplacementString() {
            System.out.println("get replacementString " + value);
            return value.getName();
        }

        public Object getValue() {
            return value;
        }
    }

    private ServiceCache serviceCache;

    public SchoolCompleteOracle(ServiceCache topicCache) {
        this.serviceCache = topicCache;
    }

    @Override
    public void requestSuggestions(final Request request,
            final Callback callback) {

        getSchoolsForString(request.getQuery(),
                new EZCallback<List<School>>() {

                    public void onSuccess(List<School> results) {

                        List<SchoolSuggestion> suggestions = new ArrayList<SchoolSuggestion>();

                        for (School school : results) {
                            suggestions.add(new SchoolSuggestion(school,
                                    request.getQuery()));
                        }
                        callback.onSuggestionsReady(request,
                                new Response(suggestions));
                    }

                });
    }

    public void getSchoolsForString(String queryString,
            AsyncCallback<List<School>> callback) {
        serviceCache.match(queryString, callback);
    }

    @Override
    public boolean isDisplayStringHTML() {
        return true;
    }

}
