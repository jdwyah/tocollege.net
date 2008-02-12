package com.apress.progwt.client.domain;

import java.io.Serializable;

import com.apress.progwt.client.domain.generated.AbstractApplicationCheckbox;

public class ApplicationCheckbox extends AbstractApplicationCheckbox
        implements Serializable, Loadable {

    public ApplicationCheckbox() {
    }

    public ApplicationCheckbox(String name) {
        setName(name);
    }

}
