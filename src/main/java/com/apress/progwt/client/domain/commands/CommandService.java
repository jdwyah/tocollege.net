package com.apress.progwt.client.domain.commands;

import com.apress.progwt.client.domain.Loadable;

public interface CommandService {

    <T> T get(Class<T> clazz, long id);

    void delete(Loadable loadable);

    void save(Loadable o);
}
