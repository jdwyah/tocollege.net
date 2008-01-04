package com.apress.progwt.client.suggest;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.School;

public class SchoolCompleter extends AbstractCompleter<School> {

    public SchoolCompleter(ServiceCache topicService,
            CompleteListener<School> completeListener) {
        super(new SchoolCompleteOracle(topicService), completeListener);

    }

}
