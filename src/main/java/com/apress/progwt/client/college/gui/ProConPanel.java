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

import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ProConPanel extends Composite {

    private ProConDispPanel pros;
    private ProConDispPanel cons;

    public ProConPanel(User thisUser, Application application,
            CollegeEntry collegeEntry) {

        HorizontalPanel mainPanel = new HorizontalPanel();

        pros = new ProConDispPanel("Pro", application.getPros(),
                collegeEntry);
        cons = new ProConDispPanel("Con", application.getCons(),
                collegeEntry);

        mainPanel.add(pros);
        mainPanel.add(cons);

        initWidget(mainPanel);

    }

    public void bindFields(Application application) {
        pros.bindFields(application.getPros());
        cons.bindFields(application.getCons());
    }

}
