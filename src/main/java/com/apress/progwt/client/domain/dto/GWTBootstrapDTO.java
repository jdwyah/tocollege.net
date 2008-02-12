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
package com.apress.progwt.client.domain.dto;

import com.apress.progwt.client.domain.GWTSerializer;
import com.apress.progwt.client.exception.InfrastructureException;

/**
 * Abstract DTO meant to be used as a bootstrapping object for GWT.
 * 
 * Look at commonGWT.ftl and GWTApp.java to see how this is used.
 * 
 * Note, all implementing subclasses must be serialized in a standard RPC
 * call somewhere, otherwise, the appropriate deserializer will not be
 * created. ie, just make an empty RPC call which returns your subtype.
 * 
 * @author Jeff Dwyer
 * 
 */
public abstract class GWTBootstrapDTO {

    private transient GWTSerializer serializer;

    public GWTBootstrapDTO() {
    }

    public GWTBootstrapDTO(GWTSerializer serializer) {
        this.serializer = serializer;
    }

    public GWTSerializer getSerializer() {
        return serializer;
    }

    public abstract String getNoscript();

    public abstract String getSerialized() throws InfrastructureException;
}
