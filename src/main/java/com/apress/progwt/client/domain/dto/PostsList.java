package com.apress.progwt.client.domain.dto;

import java.io.Serializable;
import java.util.List;

import com.apress.progwt.client.domain.ForumPost;

public class PostsList implements Serializable {

    private List<ForumPost> posts;

    private int totalCount;

    public PostsList() {
    }

    public PostsList(List<ForumPost> posts, int totalCount) {
        super();
        this.posts = posts;
        this.totalCount = totalCount;
    }

    public List<ForumPost> getPosts() {
        return posts;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setPosts(List<ForumPost> posts) {
        this.posts = posts;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
