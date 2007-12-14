package com.apress.progwt.server.gwt;

import com.google.gwt.user.client.rpc.SerializationException;

public interface ValueWriter {
    void write(ServerSerializationStreamWriter1529 stream, Object instance)
            throws SerializationException;
}
