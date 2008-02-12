package com.apress.progwt.client.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.apress.progwt.client.domain.generated.AbstractApplicationProcess;

public class ApplicationProcess extends AbstractApplicationProcess
        implements Serializable, Loadable {

    public ApplicationProcess() {
        Map<ApplicationCheckbox, Integer> appItems = new HashMap<ApplicationCheckbox, Integer>();
        System.out.println("setting app items");
        setApplicationItems(appItems);
    }

    public ApplicationCheckboxValue getItemValue(
            ApplicationCheckbox applicationStatusType) {
        System.out.println("getApItems " + getApplicationItems());
        System.out.println("get: " + applicationStatusType);
        System.out.println(getApplicationItems().get(
                applicationStatusType));
        System.out.println("foo");

        return new ApplicationCheckboxValue(getApplicationItems().get(
                applicationStatusType));
    }
}
