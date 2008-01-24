package com.apress.progwt.client.domain.forum;

import java.io.Serializable;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.User;

public class RecentForumPostTopic implements ForumTopic, Serializable {

    public RecentForumPostTopic() {
    }

    public boolean doThreadListView() {
        return false;
    }

    public String getForumDisplayName() {
        return "Recent Forum Posts";
    }

    public ForumPost getForumPost() {
        return null;
    }

    public long getId() {
        return 0;
    }

    public ForumPost getReplyInstance(User author, String title,
            String text, ForumPost thread) {
        throw new UnsupportedOperationException();
    }

    public String getUniqueForumID() {
        return "RecentForumPost" + ForumTopic.SEP + getId();
    }

}
