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

import com.apress.progwt.client.domain.forum.ForumTopic;

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
