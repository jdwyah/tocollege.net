package com.apress.progwt.client.domain;

public class UserForumPost extends ForumPost {

    public UserForumPost() {
    }

    public UserForumPost(User user, User author, String title,
            String text, ForumPost thread) {
        super(null, user, author, title, text, thread);
    }

    public UserForumPost getReplyInstance(User author, String title,
            String text, ForumPost thread) {

        return new UserForumPost(getUser(), author, title, text, thread);
    }

}
