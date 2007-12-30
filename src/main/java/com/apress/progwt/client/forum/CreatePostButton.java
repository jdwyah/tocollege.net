package com.apress.progwt.client.forum;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CreatePostButton extends Button implements ClickListener {

    private Forum<? extends ForumPost> app;
    private long schoolID;
    private User user;

    public CreatePostButton(Forum<? extends ForumPost> app, long schoolID) {
        this(app, null, schoolID, null);
    }

    public CreatePostButton(Forum<? extends ForumPost> app, User user) {
        this(app, null, null, user);
    }

    public CreatePostButton(Forum<? extends ForumPost> app,
            ForumPost thread, Long schoolID, User user) {
        super("Create New Thread");
        addClickListener(this);
        this.app = app;
        this.schoolID = schoolID;
        this.user = user;
    }

    public void onClick(Widget sender) {
        app.getLoginService().getUserOrDoLogin(
                new StdAsyncCallback<User>("Login For Create Post") {

                    public void onSuccess(User author) {
                        openCreatePost(author);
                    }
                });
    }

    protected void openCreatePost(User author) {
        CreatePostDialog cpd = new CreatePostDialog(null, author);
        cpd.center();
    }

    private void create(User author, ForumPost thread, String title,
            String text) {

    }

    private class CreatePostDialog extends DialogBox {
        public CreatePostDialog(ForumPost thread, User author) {
            super(false);
            setText("Create Post");
            setWidget(new CreatePostWidget(this, thread, author));
        }
    }

    private class CreatePostWidget extends Composite {

        private TextBox titleBox = new TextBox();
        private TextArea textBox = new TextArea();

        public CreatePostWidget(final CreatePostDialog createPostDialog,
                final ForumPost thread, final User author) {
            VerticalPanel mainP = new VerticalPanel();
            mainP.add(titleBox);
            mainP.add(textBox);

            Button cancelB = new Button("Cancel");
            Button submitB = new Button("Create Thread");

            submitB.addClickListener(new ClickListener() {

                public void onClick(Widget sender) {
                    create(author, thread, titleBox.getText(), textBox
                            .getText());
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

}
