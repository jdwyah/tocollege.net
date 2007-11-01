package com.apress.progwt.server.dao;

import java.util.List;

import com.apress.progwt.client.domain.Foo;
import com.apress.progwt.client.domain.Loadable;
import com.apress.progwt.client.domain.School;

public interface SchoolDAO {

    School getSchoolFromName(String name);

    List<School> getAllSchools();

    List<School> getSchoolsMatching(String match);

    Loadable get(Class<? extends Loadable> loadable, Long id);

    Loadable save(Loadable loadable);

    // void executeAndSaveCommand(User u, AbstractCommand command);

    Foo saveF();

    void setSchoolAtRank(long id, School school, int rank);

    void removeSchool(long id, School school);

}
