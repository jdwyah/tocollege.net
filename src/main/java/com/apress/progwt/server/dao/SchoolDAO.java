package com.apress.progwt.server.dao;

import java.util.List;

import com.apress.progwt.client.domain.Loadable;
import com.apress.progwt.client.domain.School;

public interface SchoolDAO {

    School getSchoolFromName(String name);

    List<School> getAllSchools();

    List<School> getSchoolsMatching(String match);

    Loadable get(Class<Loadable> loadable, Long id);

    void save(Loadable loadable);

}
