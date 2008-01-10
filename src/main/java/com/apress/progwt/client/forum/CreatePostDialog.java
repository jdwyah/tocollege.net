package com.apress.progwt.client.forum;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.DialogBox;

public class CreatePostDialog extends DialogBox {
    public CreatePostDialog(ForumApp<? extends ForumPost> app,
            ForumPost thread, User author) {
        super(false);
        setText("Create Post");
        setWidget(new CreatePostWidget(app, this, thread, author));
    }
}
