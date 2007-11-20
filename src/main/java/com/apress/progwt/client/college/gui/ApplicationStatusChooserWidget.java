package com.apress.progwt.client.college.gui;

import java.util.Date;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.widget.datepicker.SimpleDatePicker;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationStatusChooserWidget extends Composite {

    private ServiceCache serviceCache;
    private PopupPanel popupP;
    private Label lab;

    public ApplicationStatusChooserWidget(Application application,
            ServiceCache serviceCache) {

        this.serviceCache = serviceCache;

        ProcessType currentStatus = application.getStatus();

        lab = new Label(currentStatus.getName());
        lab.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                showOptions();
            }

        });

        VerticalPanel chooseP = new VerticalPanel();

        for (ProcessType statusType : application.getUser()
                .getStatusProcessTypes()) {
            HorizontalPanel hp = new HorizontalPanel();

            hp.add(new PChooser(statusType));

            chooseP.add(hp);
        }
        popupP = new PopupPanel(true);
        popupP.add(chooseP);
        popupP.addStyleName("TC-Popup");
        popupP.addStyleName("TC-Popup-Status");

        initWidget(lab);

    }

    private void showOptions() {
        popupP.setPopupPosition(lab.getAbsoluteLeft(), lab
                .getAbsoluteTop());
        popupP.show();

    }

    private class PChooser extends Composite implements ChangeListener {

        private ProcessType statusType;
        private SimpleDatePicker datePicker;

        public PChooser(ProcessType statusType) {
            this.statusType = statusType;

            HorizontalPanel mainP = new HorizontalPanel();

            mainP.add(new Label(statusType.getName()));
            datePicker = new SimpleDatePicker();
            datePicker.setWeekendSelectable(true);
            datePicker.addChangeListener(this);
            mainP.add(datePicker);
            mainP.add(new Label("(Clear)"));

            mainP.addStyleName("TC-ProcessChooserDate");

            initWidget(mainP);
        }

        public void onChange(Widget sender) {
            Date selected = datePicker.getSelectedDate();

            System.out.println(selected);

        }
    }
}
