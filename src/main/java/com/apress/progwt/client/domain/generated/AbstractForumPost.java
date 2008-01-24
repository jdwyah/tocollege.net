package com.apress.progwt.client.domain.generated;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.domain.forum.ForumPost;

public abstract class AbstractForumPost implements Serializable {

    private User author;
    private Date date;
    private long id;
    private String postString;

    private String postTitle;

    /**
     * The top level post, ie 'thread' that this post belongs to. Null if
     * we are the top level post ourselves.
     */
    private ForumPost threadPost;

    private Set<ForumPost> replies = new HashSet<ForumPost>();

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

    public ForumPost getThreadPost() {
        return threadPost;
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

    public void setThreadPost(ForumPost threadPost) {
        this.threadPost = threadPost;
    }

    public Set<ForumPost> getReplies() {
        return replies;
    }

    public void setReplies(Set<ForumPost> replies) {
        this.replies = replies;
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
                + ((threadPost == null) ? 0 : threadPost.hashCode());

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

        if (threadPost == null) {
            if (other.threadPost != null)
                return false;
        } else if (!threadPost.equals(other.threadPost))
            return false;

        return true;
    }

}
