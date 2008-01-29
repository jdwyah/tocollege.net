package com.apress.progwt.client.suggest;

import java.util.ArrayList;
import java.util.List;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.rpc.EZCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProcessCompleteOracle extends
        AbstractSuggestOracle<ProcessType> {

    public ProcessCompleteOracle(ServiceCache serviceCache) {
        super(serviceCache);

    }

    public void getProcessesForString(String queryString,
            AsyncCallback<List<ProcessType>> callback) {

        getServiceCache().matchProcessType(queryString, callback);
    }

    @Override
    public boolean isDisplayStringHTML() {
        return true;
    }

    @Override
    public void fireCompleteListenerFromCompleteString(
            String completeString,
            final CompleteListener<ProcessType> listener) {
        Request req = new Request(completeString);
        requestSuggestions(req, new Callback() {
            public void onSuggestionsReady(Request req, Response resp) {
                Suggestion sugg = resp.getSuggestions().iterator().next();
                listener.completed(((ProcessSuggestion) sugg).getValue());
            }
        });

    }

    @Override
    public void requestSuggestions(final Request request,
            final Callback callback) {
        getProcessesForString(request.getQuery(),
                new EZCallback<List<ProcessType>>() {

                    public void onSuccess(List<ProcessType> results) {
                        createAndReturnSuggestions(request, callback,
                                results);
                    }
                });
    }

    protected void createAndReturnSuggestions(Request request,
            Callback callback, List<? extends Object> results) {
        List<ProcessSuggestion> suggestions = new ArrayList<ProcessSuggestion>();

        for (Object obj : results) {
            ProcessType process = (ProcessType) obj;
            System.out.println("Found processType " + process);
            suggestions.add(new ProcessSuggestion(process, request
                    .getQuery()));
        }
        callback.onSuggestionsReady(request, new Response(suggestions));
    }

}
