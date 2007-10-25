package com.apress.progwt.server.service;

import java.util.List;

import com.apress.progwt.client.domain.School;
import com.apress.progwt.server.domain.SchoolAndRank;

public interface SchoolService {

    School getSchoolDetails(String schoolname);

    List<SchoolAndRank> getPopularSchools();

    List<School> getTopSchools();

}
