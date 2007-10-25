package com.apress.progwt.server.dao;

import java.util.List;

import com.apress.progwt.client.domain.School;

public interface SchoolDAO {

    School getSchoolFromName(String name);

    List<School> getAllSchools();

}
