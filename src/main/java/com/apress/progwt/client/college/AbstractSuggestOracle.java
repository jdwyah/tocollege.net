package com.apress.progwt.client.college;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SuggestOracle;

public abstract class AbstractSuggestOracle<T> extends SuggestOracle {

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
    protected String highlight(String candidate, String query) {

        int index = 0;
        int cursor = 0;

        // Create strong search string.
        StringBuffer accum = new StringBuffer();

        query = query.toLowerCase();

        index = candidate.toLowerCase().indexOf(query, index);

        if (index != -1) {

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

    public abstract T getValueFromSuggestion(Suggestion sugg);
}
