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
import com.apress.progwt.client.domain.generated.AbstractSchool;

public class School extends AbstractSchool implements Serializable,
        Loadable, HasAddress, ForumTopic {

    public School() {

    }

    public School(long id) {
        setId(id);
    }

    public School(String name) {
        setName(name);
    }

    @Override
    public String toString() {
        return "School:" + getId() + ":name:" + getName();
    }

    public String getFullAddress() {
        StringBuffer sb = new StringBuffer();
        sb.append(getAddress());
        sb.append(" ");
        sb.append(getCity());
        sb.append(", ");
        sb.append(getState());
        sb.append(" ");
        sb.append(getZip());

        return sb.toString();
        // return
        // school.getName()+school.getAddress()+school.getCity()+school.getState()+school.getZip()
    }

    public String getUniqueForumID() {
        return "School" + ForumTopic.SEP + getId();
    }

    public boolean doThreadListView() {
        return false;
    }

    /**
     * called by forumPost create(), this will create new forumpost.
     */
    public ForumPost getForumPost() {
        return null;
    }

    public ForumPost getReplyInstance(User author, String title,
            String text, ForumPost thread) {
        return new SchoolForumPost(this, author, title, text, thread);
    }

    public String getForumDisplayName() {
        return getName();
    }

}
