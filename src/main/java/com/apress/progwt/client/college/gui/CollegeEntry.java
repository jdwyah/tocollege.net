package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.domain.SchoolAndAppProcess;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CollegeEntry extends Composite {

    private Label collegeNameLabel;
    private FocusPanel fp;
    private SchoolAndAppProcess schoolAndApplication;

    public CollegeEntry(User thisUser,
            SchoolAndAppProcess schoolAndApplication) {
        this.schoolAndApplication = schoolAndApplication;

        HorizontalPanel mainPanel = new HorizontalPanel();

        collegeNameLabel = new Label(schoolAndApplication.getSchool()
                .getName());

        mainPanel.add(collegeNameLabel);

        HorizontalPanel checkBoxP = getCheckBoxes(thisUser,
                schoolAndApplication);

        checkBoxP.setStyleName("TC-App-CheckBoxes");

        mainPanel.add(checkBoxP);
        mainPanel.setStyleName("TC-CollegEntry");
        fp = new FocusPanel(mainPanel);
        initWidget(fp);

    }

    private HorizontalPanel getCheckBoxes(User thisUser,
            SchoolAndAppProcess schoolAndApplication) {
        HorizontalPanel checkBoxP = new HorizontalPanel();
        for (ProcessType processType : thisUser.getProcessTypes()) {
            ProcessValue value = schoolAndApplication.getProcess().get(
                    processType);
            if (value == null) {
                value = new ProcessValue();
            }
            checkBoxP.add(new AppCheckboxWidget(processType, value));
        }
        return checkBoxP;
    }

    public SchoolAndAppProcess getSchoolAndApplication() {
        return schoolAndApplication;
    }

    public Widget getDragHandle() {

        return collegeNameLabel;
    }
}
