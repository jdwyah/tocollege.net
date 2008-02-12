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
