package com.apress.progwt.client.forum;

import com.apress.progwt.client.domain.ForumPost;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PostLink extends Composite implements ClickListener {

    public PostLink(ForumApp<? extends ForumPost> forumApp, ForumPost post) {

        HorizontalPanel mainP = new HorizontalPanel();

        mainP.add(new Label(post.getPostTitle()));

        CreatePostButton replyB = new CreatePostButton(forumApp, post);

        mainP.add(replyB);

        initWidget(mainP);
    }

    public void onClick(Widget sender) {

    }

}
