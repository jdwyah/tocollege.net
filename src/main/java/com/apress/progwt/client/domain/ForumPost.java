/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.apress.progwt.client.domain;

import java.io.Serializable;
import java.util.Date;

import com.apress.progwt.client.domain.forum.ForumTopic;
import com.apress.progwt.client.domain.generated.AbstractForumPost;

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

    public void appendNoscript(StringBuffer addTo) {
        addTo.append("Title: ");
        addTo.append(getPostTitle());
        addTo.append("<br>\n");
        addTo.append("Post: ");
        addTo.append(getPostString());
        addTo.append("<br>\n");
        addTo.append("Topic: ");
        addTo.append(getTopic());
        addTo.append("<br>\n");
        addTo.append("Author: ");
        addTo.append(getAuthor());
        addTo.append("<p>\n");
    }

}
