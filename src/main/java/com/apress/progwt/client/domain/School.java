package com.apress.progwt.client.domain;

import java.io.Serializable;

import com.apress.progwt.client.domain.generated.AbstractSchool;

public class School extends AbstractSchool implements Serializable,
        Loadable, HasAddress {

    public School() {

    }

    @Override
    public String toString() {
        return getName();
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

}
