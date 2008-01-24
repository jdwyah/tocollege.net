package com.apress.progwt.client.domain.forum;

import java.io.Serializable;

import com.apress.progwt.client.domain.User;

public class UserForumPost extends ForumPost implements Serializable {

    private User user;

    public UserForumPost() {
    }

    public UserForumPost(User user, User author, String title,
            String text, ForumPost thread) {
        super(author, title, text, thread);
        setUser(user);
    }

    public String getUniqueForumID() {
        return "UserForumPost" + ForumTopic.SEP + getId();
    }

    public UserForumPost getReplyInstance(User author, String title,
            String text, ForumPost thread) {

        return new UserForumPost(getUser(), author, title, text, thread);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public ForumTopic getTopic() {
        return getUser();
    }

    @Override
    public String toString() {
        return "ForumPost " + getId() + " Title: " + getPostTitle()
                + " usr " + getUser() + " thread: " + getThreadPost();
    }

    @Override
    public void setTopic(ForumTopic topic) {
        setUser((User) topic);
    }

    @Override
    public Class<? extends ForumTopic> getTopicClass() {
        return User.class;
    }
}
