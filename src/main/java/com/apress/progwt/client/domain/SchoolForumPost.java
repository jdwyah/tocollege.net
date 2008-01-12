package com.apress.progwt.client.domain;

import com.apress.progwt.client.util.Logger;

public class SchoolForumPost extends ForumPost {
    public SchoolForumPost() {
    }

    public SchoolForumPost(School sc, User author, String postTitle,
            String postString, ForumPost thread) {
        super(sc, null, author, postTitle, postString, thread);
    }

    public ForumPost getReplyInstance(User author, String title,
            String text, ForumPost thread) {
        Logger.error("Not intended to be called. Should not reply ot ");
        return new SchoolForumPost(getSchool(), author, title, text,
                thread);
    }

}
