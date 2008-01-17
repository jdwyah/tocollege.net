package com.apress.progwt.client.forum;

import com.apress.progwt.client.college.gui.ext.RichTextToolbar;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.util.Logger;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CreatePostWidget extends Composite {

    private static final int REPLY_LINE_LENGTH = 75;
    private TextBox titleBox;
    private RichTextArea textArea;

    public CreatePostWidget() {

    }

    public CreatePostWidget(final ForumApp app, boolean isReply,
            final CreatePostDialog createPostDialog, final User author,
            String selection) {

        VerticalPanel mainP = new VerticalPanel();

        titleBox = new TextBox();

        textArea = new RichTextArea();

        textArea.setSize("35em", "15em");
        RichTextToolbar toolbar = new RichTextToolbar(textArea);

        HorizontalPanel hp = new HorizontalPanel();
        hp.add(new Label("Title:"));
        hp.add(titleBox);
        mainP.add(hp);

        mainP.add(toolbar);
        mainP.add(textArea);

        Button cancelB = new Button("Cancel");
        Button submitB = new Button("Create Thread");
        if (isReply) {
            submitB.setText("Post Reply");
        }

        submitB.addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
                app
                        .create(author, titleBox.getText(), textArea
                                .getHTML());
                createPostDialog.hide();
            }

        });
        cancelB.addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
                createPostDialog.hide();
            }
        });

        mainP.add(cancelB);
        mainP.add(submitB);

        initWidget(mainP);

        Logger.log("setHTML: " + selection);

        // selection = "start<script>alert('xss');</script>end";
        setHTML(makeReplyFromString(selection));
    }

    public String makeReplyFromString(String selection) {
        return makeReplyFromString(selection, REPLY_LINE_LENGTH);
    }

    public String makeReplyFromString(String selection,
            int replyLineLength) {
        int sIndex = 0;
        StringBuffer selectionSB = new StringBuffer();
        while (sIndex < selection.length()) {

            int endIndex = sIndex + replyLineLength;
            endIndex = endIndex >= selection.length() ? selection
                    .length() : endIndex;

            selectionSB.append("&gt;");
            selectionSB.append(selection.substring(sIndex, endIndex));
            selectionSB.append("<br>");
            sIndex += replyLineLength;
        }
        return selectionSB.toString();
    }

    public void setHTML(final String text) {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                textArea.setHTML(text);
            }
        });
    }
}
