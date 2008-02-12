/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.apress.progwt.client.college.gui;

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
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

        ProcessType currentStatus = application.getCurrentStatus();

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
        chooseP.add(new Label("Click Status To Choose"));
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

    private class PChooser extends Composite implements ChangeListener,
            ClickListener {

        private ProcessType statusType;
        private SimpleDatePicker datePicker;
        private Label label;

        public PChooser(ProcessType statusType) {
            this.statusType = statusType;

            HorizontalPanel mainP = new HorizontalPanel();

            label = new Label(statusType.getName());
            mainP.add(label);
            datePicker = new SimpleDatePicker();
            datePicker.setWeekendSelectable(true);
            datePicker.addChangeListener(this);
            mainP.add(datePicker);
            mainP.add(new Label("(Clear)"));

            mainP.addStyleName("TC-ProcessChooserDate");

            label.addClickListener(this);

            initWidget(mainP);
        }

        public void onChange(Widget sender) {
            Date selected = datePicker.getSelectedDate();

            Log.debug(selected.toString());

        }

        public void onClick(Widget sender) {
            datePicker.setSelectedDate(new Date());
        }
    }
}
