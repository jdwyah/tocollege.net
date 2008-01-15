package com.apress.progwt.client.domain;

import java.io.Serializable;

import com.apress.progwt.client.forum.ForumTopic;
import com.apress.progwt.client.util.Logger;

public class SchoolForumPost extends ForumPost implements Serializable {

    private School school;

    public SchoolForumPost() {
    }

    public SchoolForumPost(School sc, User author, String postTitle,
            String postString, ForumPost thread) {
        super(author, postTitle, postString, thread);
        setSchool(sc);
    }

    public String getUniqueForumID() {
        return "SchoolForumPost" + ForumTopic.SEP + getId();
    }

    public ForumPost getReplyInstance(User author, String title,
            String text, ForumPost thread) {
        Logger.error("Not intended to be called. Should not reply ot ");
        return new SchoolForumPost(getSchool(), author, title, text,
                thread);
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "ForumPost " + getId() + " Title: " + getPostTitle()
                + " sc " + getSchool() + " thread: " + getThreadPost();
    }

    @Override
    public ForumTopic getTopic() {
        return getSchool();
    }

    @Override
    public void setTopic(ForumTopic topic) {
        setSchool((School) topic);
    }

    @Override
    public Class<? extends ForumTopic> getTopicClass() {
        return School.class;
    }
}
