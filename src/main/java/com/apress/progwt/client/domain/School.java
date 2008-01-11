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
        return "School: " + getName();
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

    public boolean showForumPostText() {
        return false;
    }

    public ForumPost getForumPost() {
        throw new UnsupportedOperationException();
    }

    public ForumPost getReplyInstance(User author, String title,
            String text, ForumPost thread) {
        return new SchoolForumPost(this, author, title, text, null);
    }

}
