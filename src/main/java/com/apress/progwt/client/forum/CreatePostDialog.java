package com.apress.progwt.client.forum;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.DialogBox;

public class CreatePostDialog extends DialogBox {
    public CreatePostDialog(ForumApp<? extends ForumPost> app,
            User author, boolean isReply) {
        super(false);
        if (isReply) {
            setText("Create Reply");
        } else {
            setText("Create Post");
        }
        setWidget(new CreatePostWidget(app, isReply, this, author));
    }
}
