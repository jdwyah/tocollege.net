package com.apress.progwt.client.gears;

import com.google.gwt.gears.database.client.DatabaseException;
import com.google.gwt.gears.database.client.ResultSet;

public interface GearsRowMapper<T> {

    /**
     * Implementations should return the object representation of the
     * current row in the supplied {@link ResultSet}.
     * 
     * @see org.springframework.jdbc.core.RowMapper#mapRow
     */
    T mapRow(ResultSet rs, int rowNum) throws DatabaseException;

}