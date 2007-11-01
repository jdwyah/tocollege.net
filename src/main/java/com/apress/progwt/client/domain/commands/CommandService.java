package com.apress.progwt.client.domain.commands;

import com.apress.progwt.client.domain.School;

public interface CommandService {

    void setSchoolAtRank(School school, int rank);

    void removeSchool(School school);

}
