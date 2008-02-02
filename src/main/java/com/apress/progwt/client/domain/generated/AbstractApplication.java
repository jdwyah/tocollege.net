package com.apress.progwt.client.domain.generated;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.RatingType;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;

public abstract class AbstractApplication implements Serializable {

    private long id;
    private School school;

    private User user;

    /**
     * The sort order within the user's rankListing. Would prefereably
     * have been maintaned by hibernate wihtout us, but this had issues on
     * updates. Maintaining ourselves and using a Bag mapping
     */
    private int sortOrder;

    private Map<ProcessType, ProcessValue> process = new HashMap<ProcessType, ProcessValue>();
    private Map<RatingType, Integer> ratings = new HashMap<RatingType, Integer>();

    private List<String> pros = new ArrayList<String>();
    private List<String> cons = new ArrayList<String>();
    private String notes;

    public AbstractApplication() {
    }

    public School getSchool() {
        return school;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Map<RatingType, Integer> getRatings() {
        return ratings;
    }

    public void setRatings(Map<RatingType, Integer> ratings) {
        this.ratings = ratings;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<ProcessType, ProcessValue> getProcess() {
        return process;
    }

    public void setProcess(Map<ProcessType, ProcessValue> process) {
        this.process = process;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result
                + ((school == null) ? 0 : school.hashCode());
        result = prime * result + sortOrder;
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof AbstractApplication))
            return false;
        final AbstractApplication other = (AbstractApplication) obj;

        // are they the same DB row?
        if (id == other.id && id != 0) {
            return true;
        }
        if (process == null) {
            if (other.process != null)
                return false;
        } else if (!process.equals(other.process))
            return false;
        if (school == null) {
            if (other.school != null)
                return false;
        } else if (!school.equals(other.school))
            return false;
        if (sortOrder != other.sortOrder)
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

    public List<String> getPros() {
        return pros;
    }

    public void setPros(List<String> pros) {
        this.pros = pros;
    }

    public List<String> getCons() {
        return cons;
    }

    public void setCons(List<String> cons) {
        this.cons = cons;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
