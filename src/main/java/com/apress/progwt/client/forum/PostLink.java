package com.apress.progwt.client.forum;

import com.apress.progwt.client.domain.ForumPost;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PostLink extends Label implements ClickListener {

    public PostLink(ForumPost post) {
        super(post.getPostTitle());
        addClickListener(this);
    }

    public void onClick(Widget sender) {

    }

}
