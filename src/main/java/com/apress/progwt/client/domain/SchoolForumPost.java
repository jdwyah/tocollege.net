package com.apress.progwt.client.domain;

public class SchoolForumPost extends ForumPost {
    public SchoolForumPost() {
    }

    public SchoolForumPost(School sc, User author, String postTitle,
            String postString, ForumPost thread) {
        super(sc, null, author, postTitle, postString, thread);
    }

}
