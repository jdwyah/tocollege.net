package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.domain.School;

public class SchoolLink extends ExternalLink {
    public SchoolLink(School school) {
        super(school.getName(), "site/college/"
                + school.getName().replace(' ', '_'), true);
    }
}
