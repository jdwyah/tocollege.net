package com.apress.progwt.client.forum;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class CreatePostButton extends Button implements ClickListener {

    private ForumApp<? extends ForumPost> app;
    private boolean isReply;

    // private ForumPost thread;

    /**
     * 
     * @param app
     * @param buttonText
     * @param topic -
     *            can be null for new thread creation
     */
    public CreatePostButton(ForumApp<? extends ForumPost> app,
            boolean isReply, ForumTopic topic) {
        super("Create New Thread");
        if (isReply) {
            setText("Reply");
        }
        addClickListener(this);
        this.app = app;
        this.isReply = isReply;
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
        CreatePostDialog cpd = new CreatePostDialog(app, author, isReply);
        cpd.center();
    }

}
