package com.apress.progwt.client.gears;

import java.util.List;

import com.apress.progwt.client.json.JSONSerializable;

/**
 * Empty DB store. Used to allow us to always assume that we have a
 * ClientDB and avoid constant null checking. Just act as if nothing is
 * ever stored.
 * 
 * 
 * Alternately we could implement a map style version with something like:
 * 
 * Map<String, Map<String, List<String>>> stores;
 * 
 * which would give us some gears-like functionality on non-gears
 * supporting platforms, though it wouldn't save anything between browser
 * sessions.
 * 
 * 
 * @author Jeff Dwyer
 * 
 */
public class EmptyClientDB implements ClientDB {

    public void addToKeyedStringStore(String storeName, String key,
            String string) {
    }

    public void addToKeyedStringStore(String storeName, String key,
            JSONSerializable type) {
    }

    public void createKeyedStringStore(String storeName) {

    }

    public <T> List<T> getFromKeyedStringStore(String storeName,
            String key, GearsRowMapper<T> processTypeMapper) {
        return null;
    }

}
