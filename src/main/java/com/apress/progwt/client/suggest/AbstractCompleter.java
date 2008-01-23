package com.apress.progwt.client.suggest;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestionEvent;
import com.google.gwt.user.client.ui.SuggestionHandler;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractCompleter<T> extends Composite {

    private AbstractSuggestOracle<T> oracle;
    private CompleteListener<T> completeListener;
    private SuggestBox suggestBox;

    private Timer keyboardEnterTimer;

    public AbstractCompleter(AbstractSuggestOracle<T> oracle,
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

        Log.debug("AbstractCompleter:" + completeStr + " ");

        oracle.fireCompleteListenerFromCompleteString(completeStr,
                completeListener);

        suggestBox.setText("");
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
