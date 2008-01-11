package com.apress.progwt.client.domain;

public class SchoolForumPost extends ForumPost {
    public SchoolForumPost() {
    }

    public SchoolForumPost(School sc, User author, String postTitle,
            String postString, ForumPost thread) {
        super(sc, null, author, postTitle, postString, thread);
    }

    public ForumPost getReplyInstance(User author, String title,
            String text, ForumPost thread) {

        return new SchoolForumPost(getSchool(), author, title, text,
                thread);
    }

}
