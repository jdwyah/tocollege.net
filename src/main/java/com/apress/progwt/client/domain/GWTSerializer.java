package com.apress.progwt.client.domain;

import com.apress.progwt.client.exception.InfrastructureException;

public interface GWTSerializer {

    String serializeObject(Object postList, Class<?> clazz)
            throws InfrastructureException;

}
