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

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.json.JSONSerializable;
import com.apress.progwt.client.json.JSONSerializer;
import com.google.gwt.gears.core.client.GearsException;
import com.google.gwt.gears.database.client.Database;
import com.google.gwt.gears.database.client.DatabaseException;
import com.google.gwt.gears.database.client.ResultSet;

/**
 * SimpleGearsDatabase wraps a Gears Database with functionality to ease
 * development. Features:
 * 
 * GearsRowMapper for easier object unmarshalling, Exception wrapping,
 * Vararg usage.
 * 
 * 
 * @author Jeff Dwyer
 * 
 */
public class SimpleGearsDatabase extends Database implements ClientDB {

    public SimpleGearsDatabase() throws GearsException {
        super();
    }

    public SimpleGearsDatabase(String databaseName) throws GearsException {
        super(databaseName);
    }

    /**
     * Pass through for full SQL-Lite commands.
     */
    public ResultSet execute(String statement) {
        try {
            return super.execute(statement);
        } catch (DatabaseException e) {
            Log.error(statement + " : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Varargs execute() translater var args into String[]
     * 
     * NOTE: This just uses toString() on arguments, so be sure that the
     * object arguments will query properly with their toString() value
     * 
     * @param statement
     * @param args
     * @return
     */
    public ResultSet execute(String statement, Object... args) {

        String[] strs = new String[args.length];
        int i = 0;
        for (Object o : args) {
            strs[i] = o.toString();
            i++;
        }
        try {
            return execute(statement, strs);
        } catch (DatabaseException e) {
            Log.error(statement + " : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Execute query, then translate rows using a GearsRowMapper.
     * 
     * Note:Uses varargs execute() method, so all Objects arguments will
     * have toString() called on them.
     * 
     * @param <T>
     * @param sql
     * @param mapper
     * @param args
     * @return
     */
    public <T> List<T> query(String sql, GearsRowMapper<T> mapper,
            Object... args) {

        try {
            ResultSet rs = execute(sql, args);
            List<T> rtn = new ArrayList<T>();

            for (int i = 0; rs.isValidRow(); ++i, rs.next()) {
                rtn.add(mapper.mapRow(rs, i));
            }
            rs.close();
            return rtn;
        } catch (DatabaseException e) {
            Log.error(sql + " : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Helper method when all you want is a single List<String>
     * 
     * @param sql
     * @param args
     * @return
     */
    public List<String> queryForStringList(String sql, Object... args) {
        return query(sql, new StringMapper(), args);
    }

    public void createKeyedStringStore(String tableName) {
        execute("drop table if exists " + tableName);
        execute("create table if not exists " + tableName
                + " (key varchar(255), json text )");
    }

    public void addToKeyedStringStore(String tableName, String key,
            String value) {
        execute("insert into " + tableName + " values (?, ?)", key, value);
    }

    public <T> List<T> getFromKeyedStringStore(String tableName,
            String key, GearsRowMapper<T> mapper) {

        return query("select json from " + tableName + " where key = ?",
                mapper, key);

    }

    /**
     * Perform serialization before going into the store. Your RowMapper
     * should perform deserialization.
     * 
     * @param tableName
     * @param key
     * @param object
     */
    public void addToKeyedStringStore(String tableName, String key,
            JSONSerializable object) {
        String serialized = JSONSerializer.serialize(object);
        execute("insert into " + tableName + " values (?, ?)", key,
                serialized);

    }
}
