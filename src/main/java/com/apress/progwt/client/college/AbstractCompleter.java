package com.apress.progwt.client.college;

import com.apress.progwt.client.college.gui.CompleteListener;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestionEvent;
import com.google.gwt.user.client.ui.SuggestionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public abstract class AbstractCompleter<T> extends Composite {

    private AbstractSuggestOracle oracle;
    private CompleteListener<T> completeListener;
    private SuggestBox suggestBox;

    private Timer keyboardEnterTimer;

    public AbstractCompleter(AbstractSuggestOracle oracle,
            CompleteListener<T> completeListener) {
        super();
        this.oracle = oracle;
        this.completeListener = completeListener;

        suggestBox = new SuggestBox(oracle);
        suggestBox.addEventHandler(new SuggestionHandler() {

            public void onSuggestionSelected(SuggestionEvent event) {
                System.out.println("On Suggestion Selected! "
                        + event.getSelectedSuggestion()
                                .getReplacementString());

                // Important, this prevents duplications
                if (keyboardEnterTimer != null) {
                    keyboardEnterTimer.cancel();
                }

                complete(event.getSelectedSuggestion()
                        .getReplacementString());
            }
        });

        suggestBox.addKeyboardListener(new KeyboardListenerAdapter() {
            // @Override
            public void onKeyPress(Widget sender, char keyCode,
                    int modifiers) {
                if (keyCode == KEY_ENTER) {

                    keyboardEnterTimer = new Timer() {
                        // @Override
                        public void run() {
                            complete(suggestBox.getText());
                        }
                    };
                    keyboardEnterTimer.schedule(400);
                }
            }
        });
        initWidget(suggestBox);
    }

    /**
     * public so we can call this at any time
     */
    public void complete() {
        complete(suggestBox.getText());
    }

    /**
     * Careful to prevent dupes, one from enter key keyboard listener, one
     * from the enter key selecting a suggestion. We need the keyboard
     * listener because we want the enter key to add the current text when
     * there's no suggestion.
     * 
     * @param completeStr
     */
    private void complete(final String completeStr) {

        System.out.println("AbstractCompleter:" + completeStr + " ");

        oracle.requestSuggestions(new Request(completeStr),
                new Callback() {

                    public void onSuggestionsReady(Request request,
                            Response response) {

                        Suggestion sugg = response.getSuggestions()
                                .iterator().next();

                        System.out
                                .println("AbstractCompleter.complete response "
                                        + sugg);
                        completeListener.completed((T) oracle
                                .getValueFromSuggestion(sugg));
                        suggestBox.setText("");
                    }
                });

        // oracle.getSchoolsForString(completeStr,
        // new EZCallback<List<School>>() {
        //
        // public void onSuccess(List<School> result) {
        //
        // completeListener.completed(result.get(0));
        // suggestBox.setText("");
        // }
        // });

    }

    public void setText(String string) {
        suggestBox.setText(string);
    }

    public String getText() {
        return suggestBox.getText();
    }

    public void setFocus(final boolean b) {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                suggestBox.setFocus(b);
            }
        });

    }

}
