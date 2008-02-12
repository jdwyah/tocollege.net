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
