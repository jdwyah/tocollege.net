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
