package com.apress.progwt.client.domain.dto;

import java.io.Serializable;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.GWTSerializer;
import com.apress.progwt.client.exception.InfrastructureException;
import com.apress.progwt.client.forum.ForumTopic;

public class ForumBootstrap extends GWTBootstrapDTO implements
        Serializable {

    private ForumTopic forumTopic;
    private PostsList postsList;

    public ForumBootstrap() {
    }

    public ForumBootstrap(GWTSerializer serializer, PostsList postsList,
            ForumTopic forumTopic) {
        super(serializer);
        this.forumTopic = forumTopic;
        this.postsList = postsList;
    }

    @Override
    public String getNoscript() {
        StringBuffer sb = new StringBuffer();
        for (ForumPost fp : postsList.getPosts()) {
            sb.append(fp);
            sb.append("<p>");
        }
        return sb.toString();
    }

    @Override
    public String getSerialized() throws InfrastructureException {
        return getSerializer()
                .serializeObject(this, ForumBootstrap.class);
    }

    public ForumTopic getForumTopic() {
        return forumTopic;
    }

    public void setForumTopic(ForumTopic forumTopic) {
        this.forumTopic = forumTopic;
    }

    public PostsList getPostsList() {
        return postsList;
    }

    public void setPostsList(PostsList postsList) {
        this.postsList = postsList;
    }

}
