package com.apress.progwt.client.domain.generated;

import java.io.Serializable;

public class AbstractForumPost implements Serializable {

    private long id;
    private long threadID;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getThreadID() {
        return threadID;
    }

    public void setThreadID(long threadID) {
        this.threadID = threadID;
    }

}
