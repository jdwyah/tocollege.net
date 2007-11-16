package com.apress.progwt.client.domain.commands;

import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.exception.SiteException;

public interface CommandService {

    void setSchoolAtRank(School school, int rank);

    void removeSchool(School school);

    void saveProcessValue(long schoolAppID, long processTypeID,
            ProcessValue value) throws SiteException;

    void saveRatingCommand(SaveRatingCommand saveRatingCommand);

}
