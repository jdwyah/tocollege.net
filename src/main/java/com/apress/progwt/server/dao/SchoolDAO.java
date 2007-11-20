package com.apress.progwt.server.dao;

import java.util.List;

import com.apress.progwt.client.domain.Foo;
import com.apress.progwt.client.domain.Loadable;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.RatingType;
import com.apress.progwt.client.domain.School;

public interface SchoolDAO {

    School getSchoolFromName(String name);

    List<School> getAllSchools();

    List<School> getSchoolsMatching(String match);

    Loadable get(Class<? extends Loadable> loadable, Long id);

    Loadable save(Loadable loadable);

    Foo saveF();

    List<ProcessType> matchProcessType(String queryString);

    ProcessType getProcessForName(String string);

    List<ProcessType> getDefaultProcessTypes();

    List<RatingType> getDefaultRatingTypes();

}
