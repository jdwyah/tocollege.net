package com.apress.progwt.client.gears;

import java.util.List;

import com.apress.progwt.client.json.JSONSerializable;

/**
 * Generalized interface for a ClientDB. Could be implemented with
 * in-memory, gears, cookies or empty solutions.
 * 
 * @author Jeff Dwyer
 * 
 */
public interface ClientDB {

    void createKeyedStringStore(String storeName);

    void addToKeyedStringStore(String storeName, String key, String string);

    <T> List<T> getFromKeyedStringStore(String storeName, String key,
            GearsRowMapper<T> processTypeMapper);

    void addToKeyedStringStore(String storeName, String key,
            JSONSerializable type);

}
