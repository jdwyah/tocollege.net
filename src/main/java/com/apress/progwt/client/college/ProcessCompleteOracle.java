package com.apress.progwt.client.college;

import java.util.ArrayList;
import java.util.List;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.rpc.EZCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProcessCompleteOracle extends
        AbstractSuggestOracle<ProcessType> {

    protected class ProcessSuggestion implements Suggestion {
        private final ProcessType value;
        private String query;

        public ProcessSuggestion(ProcessType processType, String query) {
            this.value = processType;
            this.query = query;
        }

        public String getDisplayString() {
            return highlight(value.getName(), query);
        }

        public String getReplacementString() {
            System.out.println("get replacementString " + value);
            return value.getName();
        }

        public ProcessType getValue() {
            return value;
        }
    }

    private ServiceCache serviceCache;

    public ProcessCompleteOracle(ServiceCache topicCache) {
        this.serviceCache = topicCache;
    }

    @Override
    public void requestSuggestions(final Request request,
            final Callback callback) {

        getProcessesForString(request.getQuery(),
                new EZCallback<List<ProcessType>>() {

                    public void onSuccess(List<ProcessType> results) {

                        List<ProcessSuggestion> suggestions = new ArrayList<ProcessSuggestion>();

                        for (ProcessType process : results) {
                            System.out.println("Found processType "
                                    + process);
                            suggestions.add(new ProcessSuggestion(
                                    process, request.getQuery()));
                        }
                        callback.onSuggestionsReady(request,
                                new Response(suggestions));
                    }

                });
    }

    public void getProcessesForString(String queryString,
            AsyncCallback<List<ProcessType>> callback) {
        serviceCache.matchProcessType(queryString, callback);
    }

    @Override
    public boolean isDisplayStringHTML() {
        return true;
    }

    @Override
    public ProcessType getValueFromSuggestion(Suggestion sugg) {
        return ((ProcessSuggestion) sugg).getValue();
    }

}
