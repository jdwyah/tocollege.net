package com.apress.progwt.client.college.gui;

import com.apress.progwt.client.domain.ApplicationProcess;
import com.apress.progwt.client.domain.ApplicationCheckbox;
import com.apress.progwt.client.domain.ApplicationCheckboxValue;
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

        ApplicationProcess application = schoolAndApplication
                .getApplication();

        HorizontalPanel checkBoxP = new HorizontalPanel();
        checkBoxP.setStyleName("TC-App-CheckBoxes");

        for (ApplicationCheckbox appCheckType : thisUser
                .getApplicationStatusTypes()) {

            ApplicationCheckboxValue value = application
                    .getItemValue(appCheckType);

            checkBoxP.add(new AppCheckboxWidget(appCheckType, value));

        }

        mainPanel.add(checkBoxP);
        mainPanel.setStyleName("TC-CollegEntry");
        fp = new FocusPanel(mainPanel);
        initWidget(fp);

    }

    public SchoolAndAppProcess getSchoolAndApplication() {
        return schoolAndApplication;
    }

    public Widget getDragHandle() {

        return collegeNameLabel;
    }
}
