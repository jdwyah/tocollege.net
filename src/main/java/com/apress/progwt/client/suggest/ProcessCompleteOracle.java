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

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
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
            Log.debug("Found processType " + process);
            suggestions.add(new ProcessSuggestion(process, request
                    .getQuery()));
        }
        callback.onSuggestionsReady(request, new Response(suggestions));
    }

}
