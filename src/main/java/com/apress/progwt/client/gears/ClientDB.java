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

    void addToKeyedStringStore(String storeName, String key,
            JSONSerializable type);

    void addToKeyedStringStore(String storeName, String key, String string);

    void createKeyedStringStore(String storeName);

    <T> List<T> getFromKeyedStringStore(String storeName, String key,
            GearsRowMapper<T> processTypeMapper);

}
