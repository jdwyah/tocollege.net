package com.apress.progwt.client.college;

import com.apress.progwt.client.college.gui.CompleteListener;
import com.apress.progwt.client.domain.School;

public class SchoolCompleter extends AbstractCompleter<School> {

    public SchoolCompleter(ServiceCache topicService,
            CompleteListener<School> completeListener) {
        super(new SchoolCompleteOracle(topicService), completeListener);

    }

}
