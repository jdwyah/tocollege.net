package com.apress.progwt.client.suggest;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.ProcessType;

public class ProcessCompleter extends AbstractCompleter<ProcessType> {

    public ProcessCompleter(ServiceCache serviceCache,
            CompleteListener<ProcessType> completeListener) {
        super(new ProcessCompleteOracle(serviceCache), completeListener);
    }

}
