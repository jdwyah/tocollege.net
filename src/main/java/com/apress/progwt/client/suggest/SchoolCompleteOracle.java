package com.apress.progwt.client.suggest;

import java.util.ArrayList;
import java.util.List;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.rpc.EZCallback;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SchoolCompleteOracle extends AbstractSuggestOracle<School> {

    protected class SchoolSuggestion implements Suggestion {
        private String schoolName;
        private String query;

        public SchoolSuggestion(String schoolName, String query) {
            this.schoolName = schoolName;
            this.query = query;
        }

        public String getDisplayString() {
            return highlight(schoolName, query);
        }

        public String getReplacementString() {
            return schoolName;
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
                new EZCallback<List<String>>() {

                    public void onSuccess(List<String> results) {

                        List<SchoolSuggestion> suggestions = new ArrayList<SchoolSuggestion>();

                        System.out.println("SchoolCompleteOracle q: "
                                + request.getQuery() + " res: "
                                + results.size());
                        for (String schoolName : results) {
                            suggestions.add(new SchoolSuggestion(
                                    schoolName, request.getQuery()));
                        }
                        callback.onSuggestionsReady(request,
                                new Response(suggestions));
                    }

                });
    }

    private void getSchoolsForString(String queryString,
            AsyncCallback<List<String>> callback) {
        serviceCache.match(queryString, callback);
    }

    @Override
    public boolean isDisplayStringHTML() {
        return true;
    }

    @Override
    public void fireCompleteListenerFromCompleteString(
            String completeString, final CompleteListener<School> listener) {
        serviceCache.getSchoolDetails(completeString,
                new StdAsyncCallback<School>("Get School") {
                    @Override
                    public void onSuccess(School result) {
                        super.onSuccess(result);
                        listener.completed(result);
                    }
                });

    }

}
