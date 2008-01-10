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
    /**
     * If this is a SchoolForumPost, this should be not null and is the
     * 'topic' of this post's thread. Otherwise null.
     */
    private School school;

    /**
     * The top level post, ie 'thread' that this post belongs to. Null if
     * we are the top level post ourselves.
     */
    private ForumPost threadPost;

    /**
     * If this is a UserForumPost, this should be not null and is the
     * 'topic' of this post's thread. Otherwise null.
     */
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result
                + ((postString == null) ? 0 : postString.hashCode());
        result = prime * result
                + ((postTitle == null) ? 0 : postTitle.hashCode());
        result = prime * result
                + ((school == null) ? 0 : school.hashCode());
        result = prime * result
                + ((threadPost == null) ? 0 : threadPost.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof AbstractForumPost))
            return false;
        final AbstractForumPost other = (AbstractForumPost) obj;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (postString == null) {
            if (other.postString != null)
                return false;
        } else if (!postString.equals(other.postString))
            return false;
        if (postTitle == null) {
            if (other.postTitle != null)
                return false;
        } else if (!postTitle.equals(other.postTitle))
            return false;
        if (school == null) {
            if (other.school != null)
                return false;
        } else if (!school.equals(other.school))
            return false;
        if (threadPost == null) {
            if (other.threadPost != null)
                return false;
        } else if (!threadPost.equals(other.threadPost))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

}
