package com.apress.progwt.client.domain;

public class ApplicationAndScore implements
        Comparable<ApplicationAndScore> {

    private Application application;

    private int score;
    private int total;

    public ApplicationAndScore(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int compareTo(ApplicationAndScore o) {
        return o.getScore() - getScore();
    }

}
