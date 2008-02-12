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

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.domain.forum.ForumTopic;

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
        Log.error("Not intended to be called. Should not reply to ");
        throw new UnsupportedOperationException(
                "Not intended to be called. Should not reply to ");
        // return new SchoolForumPost(getSchool(), author, title, text,
        // thread);
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
