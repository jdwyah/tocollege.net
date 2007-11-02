package com.apress.progwt.client.college;

import com.apress.progwt.client.college.gui.CompleteListener;
import com.apress.progwt.client.domain.ProcessType;

public class ProcessCompleter extends AbstractCompleter<ProcessType> {

    public ProcessCompleter(ServiceCache serviceCache,
            CompleteListener<ProcessType> completeListener) {
        super(new ProcessCompleteOracle(serviceCache), completeListener);
    }

}
