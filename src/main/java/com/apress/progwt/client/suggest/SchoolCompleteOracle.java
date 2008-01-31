package com.apress.progwt.client.suggest;

import java.util.ArrayList;
import java.util.List;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.rpc.EZCallback;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SchoolCompleteOracle extends AbstractSuggestOracle<School> {

    public SchoolCompleteOracle(ServiceCache serviceCache) {
        super(serviceCache);
    }

    /**
     * Create the suggstion objects from a list of strings and return to
     * the callback.
     * 
     * @param request
     * @param callback
     * @param results
     */

    protected void createAndReturnSuggestions(Request request,
            Callback callback, List<? extends Object> results) {

        List<SchoolSuggestion> suggestions = new ArrayList<SchoolSuggestion>(
                results.size());
        for (Object schoolNameObj : results) {
            String schoolName = (String) schoolNameObj;
            suggestions.add(new SchoolSuggestion(schoolName, request
                    .getQuery()));
        }
        callback.onSuggestionsReady(request, new Response(suggestions));
    }

    private void getSchoolsForString(String queryString,
            AsyncCallback<List<String>> callback) {
        getServiceCache().matchSchool(queryString, callback);
    }

    @Override
    public boolean isDisplayStringHTML() {
        return true;
    }

    @Override
    public void fireCompleteListenerFromCompleteString(
            String completeString, final CompleteListener<School> listener) {
        getServiceCache().getSchoolDetails(completeString,
                new StdAsyncCallback<School>("Get School") {
                    @Override
                    public void onSuccess(School result) {
                        super.onSuccess(result);
                        listener.completed(result);
                    }
                });

    }

    @Override
    public void requestSuggestions(final Request request,
            final Callback callback) {
        getSchoolsForString(request.getQuery(),
                new EZCallback<List<String>>() {
                    public void onSuccess(List<String> results) {
                        createAndReturnSuggestions(request, callback,
                                results);

                    }
                });
    }

}
