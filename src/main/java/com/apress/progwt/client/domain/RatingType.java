package com.apress.progwt.client.domain;

import java.io.Serializable;

import com.apress.progwt.client.domain.generated.AbstractRatingType;

public class RatingType extends AbstractRatingType implements
        Serializable, Loadable {
    public RatingType() {
    }

    public RatingType(String name) {
        setName(name);
    }

}
