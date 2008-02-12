/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
