package com.apress.progwt.client.gears;

import com.google.gwt.gears.database.client.DatabaseException;
import com.google.gwt.gears.database.client.ResultSet;

public class StringMapper implements GearsRowMapper<String> {

    public String mapRow(ResultSet rs, int rowNum)
            throws DatabaseException {
        return rs.getFieldAsString(0);
    }

}