package com.apress.progwt.client.domain.forum;

import com.apress.progwt.client.domain.User;

public interface ForumTopic {

    /**
     * unique ID to specify any forum page.
     * 
     * @return
     */
    String getUniqueForumID();

    /**
     * if false, alternative is doPostListView
     * 
     * @return
     */
    boolean doThreadListView();

    ForumPost getReplyInstance(User author, String title, String text,
            ForumPost thread);

    ForumPost getForumPost();

    public static String SEP = "~";

    String getForumDisplayName();

    long getId();

}
