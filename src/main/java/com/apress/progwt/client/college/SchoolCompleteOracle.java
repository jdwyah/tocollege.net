package com.apress.progwt.client.college;

import java.util.ArrayList;
import java.util.List;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.rpc.EZCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SuggestOracle;

public class SchoolCompleteOracle extends SuggestOracle {

    protected class SchoolSuggestion implements Suggestion {
        private final School value;
        private String query;

        public SchoolSuggestion(School school, String query) {
            this.value = school;
            this.query = query;
        }

        public String getDisplayString() {

            return highlight(value.getName(), query);

        }

        /**
         * odd. can't return the TI, since .toString() is called on it and
         * that is put in the box. worse, there's no way to call
         * suggestBox, getSelectedValue()
         */
        public Object getValue() {
            System.out.println("get value " + value);
            return value.getName();
        }

        public String getReplacementString() {
            System.out.println("get replacementString " + value);
            return value.getName();
        }
    }

    private TopicCache serviceCache;

    public SchoolCompleteOracle(TopicCache topicCache) {
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

    private static HTML convertMe = new HTML();
    private static final char WHITESPACE_CHAR = ' ';

    private String escapeText(String escapeMe) {
        convertMe.setText(escapeMe);
        String escaped = convertMe.getHTML();
        return escaped;
    }

    /**
     * Simpler than the Google MultiWordSuggest highlighter in that it
     * will only highlight the first occurrence
     * 
     * @param candidate
     * @param query
     * @return
     */
    private String highlight(String candidate, String query) {

        int index = 0;
        int cursor = 0;

        // Create strong search string.
        StringBuffer accum = new StringBuffer();

        query = query.toLowerCase();

        index = candidate.toLowerCase().indexOf(query, index);

        if (index == -1) {
            accum.append(escapeText(candidate));
        } else {
            int endIndex = index + query.length();
            String part1 = escapeText(candidate.substring(cursor, index));
            String part2 = escapeText(candidate
                    .substring(index, endIndex));
            cursor = endIndex;
            accum.append(part1).append("<strong>").append(part2).append(
                    "</strong>");
        }

        // Finish creating the formatted string.
        String end = candidate.substring(cursor);
        accum.append(escapeText(end));

        return accum.toString();
    }
}
