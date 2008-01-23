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
