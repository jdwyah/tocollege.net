package com.apress.progwt.client.college;

import java.util.List;

import com.apress.progwt.client.college.gui.CompleteListener;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.rpc.EZCallback;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestionEvent;
import com.google.gwt.user.client.ui.SuggestionHandler;
import com.google.gwt.user.client.ui.Widget;

public class SchoolCompleter extends Composite {

    private ServiceCache topicService;
    private SchoolCompleteOracle oracle;
    private CompleteListener completeListener;
    private SuggestBox suggestBox;

    private Timer keyboardEnterTimer;

    public SchoolCompleter(ServiceCache topicService) {
        super();
        this.topicService = topicService;
        if (oracle == null) {
            oracle = new SchoolCompleteOracle(topicService);
        }
        suggestBox = new SuggestBox(oracle);

        initWidget(suggestBox);
    }

    public void setCompleteListener(CompleteListener completeListener) {

        this.completeListener = completeListener;
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

        System.out.println("TopicCompleter:" + completeStr + " ");

        oracle.getSchoolsForString(completeStr,
                new EZCallback<List<School>>() {

                    public void onSuccess(List<School> result) {
                        completeListener.completed(result.get(0));
                        suggestBox.setText("");
                    }
                });

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
