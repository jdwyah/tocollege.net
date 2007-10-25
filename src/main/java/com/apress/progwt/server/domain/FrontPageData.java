package com.apress.progwt.server.domain;

import java.util.LinkedList;
import java.util.List;

import com.apress.progwt.client.domain.Post;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.UserService;

public class FrontPageData {

    private List<School> topSchools;
    private List<SchoolAndRank> popularSchools;
    private List<User> topUsers;
    private List<Post> forumPosts;

    public FrontPageData(UserService userService,
            SchoolService schoolService) {

        setTopSchools(schoolService.getTopSchools());
        setPopularSchools(schoolService.getPopularSchools());

        setTopUsers(userService.getTopUsers());
        forumPosts = new LinkedList<Post>();
    }

    public List<School> getTopSchools() {
        return topSchools;
    }

    public void setTopSchools(List<School> topSchools) {
        this.topSchools = topSchools;
    }

    public List<SchoolAndRank> getPopularSchools() {
        return popularSchools;
    }

    public void setPopularSchools(List<SchoolAndRank> popularSchools) {
        this.popularSchools = popularSchools;
    }

    public List<User> getTopUsers() {
        return topUsers;
    }

    public void setTopUsers(List<User> topUsers) {
        this.topUsers = topUsers;
    }

    public List<Post> getForumPosts() {
        return forumPosts;
    }

    public void setForumPosts(List<Post> forumPosts) {
        this.forumPosts = forumPosts;
    }

}
