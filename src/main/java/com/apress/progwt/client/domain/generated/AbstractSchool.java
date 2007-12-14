package com.apress.progwt.client.domain.generated;

import java.io.Serializable;

public abstract class AbstractSchool implements Serializable {

    private long id;

    private String name;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String phone;
    private String website;
    private String schoolType;
    private String awards;
    private String campus;
    private int students;
    private int undergrads;
    private String varsity;
    private int ipedsID;
    private int opeID;
    private String housing;
    private double latitude;
    private double longitude;

    public AbstractSchool() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }

    public int getUndergrads() {
        return undergrads;
    }

    public void setUndergrads(int undergrads) {
        this.undergrads = undergrads;
    }

    public String getVarsity() {
        return varsity;
    }

    public void setVarsity(String varsity) {
        this.varsity = varsity;
    }

    public int getIpedsID() {
        return ipedsID;
    }

    public void setIpedsID(int ipedsID) {
        this.ipedsID = ipedsID;
    }

    public int getOpeID() {
        return opeID;
    }

    public void setOpeID(int opeID) {
        this.opeID = opeID;
    }

    public String getHousing() {
        return housing;
    }

    public void setHousing(String housing) {
        this.housing = housing;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof AbstractSchool))
            return false;
        final AbstractSchool other = (AbstractSchool) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
