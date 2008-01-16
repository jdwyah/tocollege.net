package com.apress.progwt.server.domain;

import java.util.List;

import com.apress.progwt.client.domain.ForumPost;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.server.service.SchoolService;
import com.apress.progwt.server.service.UserService;

public class FrontPageData {

    private List<School> topSchools;
    private List<SchoolPopularity> popularSchools;
    private List<User> topUsers;
    private List<ForumPost> forumPosts;

    public FrontPageData(UserService userService,
            SchoolService schoolService) {

        setTopSchools(schoolService.getTopSchools(0, 10));
        setPopularSchools(schoolService.getPopularSchools());

        setTopUsers(userService.getTopUsers(5));
        setForumPosts(schoolService.getRecentForumPosts(0, 10));

    }

    public List<School> getTopSchools() {
        return topSchools;
    }

    public void setTopSchools(List<School> topSchools) {
        this.topSchools = topSchools;
    }

    public List<SchoolPopularity> getPopularSchools() {
        return popularSchools;
    }

    public void setPopularSchools(List<SchoolPopularity> popularSchools) {
        this.popularSchools = popularSchools;
    }

    public List<User> getTopUsers() {
        return topUsers;
    }

    public void setTopUsers(List<User> topUsers) {
        this.topUsers = topUsers;
    }

    public List<ForumPost> getForumPosts() {
        return forumPosts;
    }

    public void setForumPosts(List<ForumPost> forumPosts) {
        this.forumPosts = forumPosts;
    }

}
