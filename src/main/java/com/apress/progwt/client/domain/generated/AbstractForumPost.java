package com.apress.progwt.client.domain.generated;

import java.io.Serializable;
import java.util.Date;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;

public abstract class AbstractForumPost implements Serializable {

    private User author;
    private Date date;
    private long id;
    private String postString;

    private String postTitle;
    private School school;

    private ForumPost threadPost;
    private User user;

    public User getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public String getPostString() {
        return postString;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public School getSchool() {
        return school;
    }

    public ForumPost getThreadPost() {
        return threadPost;
    }

    public User getUser() {
        return user;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPostString(String postString) {
        this.postString = postString;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void setThreadPost(ForumPost threadPost) {
        this.threadPost = threadPost;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
