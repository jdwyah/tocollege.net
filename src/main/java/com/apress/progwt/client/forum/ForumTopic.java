package com.apress.progwt.client.forum;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.User;

public interface ForumTopic {
    String getUniqueForumID();

    boolean showForumPostText();

    ForumPost getReplyInstance(User author, String title, String text,
            ForumPost thread);

    ForumPost getForumPost();

    public static String SEP = "~";

}
