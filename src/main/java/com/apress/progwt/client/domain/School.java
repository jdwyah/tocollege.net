package com.apress.progwt.client.domain;

import java.io.Serializable;

import com.apress.progwt.client.domain.generated.AbstractSchool;
import com.apress.progwt.client.forum.ForumTopic;

public class School extends AbstractSchool implements Serializable,
        Loadable, HasAddress, ForumTopic {

    public School() {

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
