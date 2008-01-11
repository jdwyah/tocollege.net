package com.apress.progwt.client.domain;

import java.io.Serializable;
import java.util.Date;

import com.apress.progwt.client.domain.generated.AbstractForumPost;
import com.apress.progwt.client.forum.ForumTopic;

public abstract class ForumPost extends AbstractForumPost implements
        Serializable, Loadable, ForumTopic {

    /**
     * not guaranteed to equal getReplies().size() since it's not lo
     */
    private int replyCount;

    public ForumPost() {
    }

    /**
     * 
     * 
     * @param school -
     *            the school this post is about - can be null
     * @param user -
     *            the user this post is about - can be null
     * @param author -
     *            the author of this post
     * @param postString -
     *            threadPost can be null if this is a new thread
     * @param threadPost -
     *            If this is null, this ForumPost is a top level post. ie,
     *            a thread. If not null, it is a response to this thread.
     *            All posts in a thread should have the same threadPost.
     */
    public ForumPost(School school, User user, User author,
            String postTitle, String postString, ForumPost threadPost) {
        setSchool(school);
        setUser(user);
        setAuthor(author);
        setPostTitle(postTitle);
        setPostString(postString);
        setThreadPost(threadPost);
        setDate(new Date());
        if (threadPost == null && postTitle == null) {
            throw new UnsupportedOperationException(
                    "First Threads Must Have Titles");
        }
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getUniqueForumID() {
        return "ForumPost" + ForumTopic.SEP + getId();
    }

    public boolean showForumPostText() {
        return true;
    }

    public ForumPost getForumPost() {
        return this;
    }

    @Override
    public String toString() {
        return "ForumPost " + getId() + " Title: " + getPostTitle()
                + " sc " + getSchool() + " usr " + getUser();
    }

}
