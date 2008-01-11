package com.apress.progwt.client.forum;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CreatePostWidget extends Composite {

    private TextBox titleBox = new TextBox();
    private TextArea textBox = new TextArea();

    public CreatePostWidget(final ForumApp<? extends ForumPost> app,
            boolean isReply, final CreatePostDialog createPostDialog,
            final User author) {
        VerticalPanel mainP = new VerticalPanel();
        mainP.add(titleBox);
        mainP.add(textBox);

        Button cancelB = new Button("Cancel");
        Button submitB = new Button("Create Thread");
        if (isReply) {
            submitB.setText("Post Reply");
        }

        submitB.addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
                app.create(author, titleBox.getText(), textBox.getText());
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
    }
}
