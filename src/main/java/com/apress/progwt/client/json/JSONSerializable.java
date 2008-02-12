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
package com.apress.progwt.client.json;

/**
 * Empty Interface. Classes that implement this interface should be sure
 * to add serialization hooks in JSONSerializer.java
 * 
 * We could make this interface have serialize() & deserialize() methods,
 * but if these objects go to the server they will explode because the
 * JSON classes are not in gwt-server.jar
 * 
 * @author Jeff Dwyer
 * 
 */
public interface JSONSerializable {

}
