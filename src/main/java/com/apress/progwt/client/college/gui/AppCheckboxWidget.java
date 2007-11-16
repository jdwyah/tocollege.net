package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.commands.SaveProcessCommand;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AppCheckboxWidget extends Composite implements ClickListener {

    private ProcessType appCheckType;
    private ProcessValue value;
    private Label theLabel;
    private ServiceCache serviceCache;
    private Application application;

    public AppCheckboxWidget(ProcessType appCheckType,
            ProcessValue value, Application application,
            ServiceCache serviceCache) {
        this.appCheckType = appCheckType;
        this.value = value;
        this.application = application;
        this.serviceCache = serviceCache;

        theLabel = new Label();

        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.add(theLabel);

        theLabel.setText(value.getString(appCheckType));

        theLabel.addClickListener(this);

        initWidget(mainPanel);

        setStyleName("TC-App-CheckBox");
    }

    public void onClick(Widget widg) {
        value.increment(appCheckType);
        theLabel.setText(value.getString(appCheckType));

        SaveProcessCommand command = new SaveProcessCommand(application,
                appCheckType, value);

        serviceCache.executeCommand(command,
                new StdAsyncCallback<Boolean>("Save Process Value") {
                    public void onSuccess(Boolean result) {
                        super.onSuccess(result);
                    }
                });
    }
}
