package com.apress.progwt.server.domain;

import com.apress.progwt.client.domain.School;

public class SchoolPopularity {

    private School school;
    private double rank;

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public SchoolPopularity(School school, double rank) {
        super();
        this.school = school;
        this.rank = rank;
    }
}
