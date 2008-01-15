package com.apress.progwt.client.domain;

import java.io.Serializable;
import java.util.Date;

import com.apress.progwt.client.domain.generated.AbstractForumPost;
import com.apress.progwt.client.forum.ForumTopic;

public abstract class ForumPost extends AbstractForumPost implements
        Serializable, Loadable, ForumTopic {

    /**
     * not guaranteed to equal getReplies().size() since it's not loaded
     * from the DB. It's used as essentially a DTO field.
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
    public ForumPost(User author, String postTitle, String postString,
            ForumPost threadPost) {

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

    public boolean doThreadListView() {
        return true;
    }

    public ForumPost getForumPost() {
        return this;
    }

    public String getForumDisplayName() {
        return getPostTitle();
    }

    public abstract ForumTopic getTopic();

    public abstract void setTopic(ForumTopic topic);

    public abstract Class<? extends ForumTopic> getTopicClass();

}
