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

import com.apress.progwt.client.college.gui.timeline.TimelineController;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class ProcessValueEditWidget extends Composite implements
        ClickListener {

    protected ProcessType processType;
    protected ProcessValue value;
    protected TimelineController controller;

    public ProcessValueEditWidget(TimelineController controller,
            ProcessType processType, ProcessValue value) {
        this.processType = processType;
        this.value = value;
        this.controller = controller;

    }

    public void onClick(Widget widg) {
        value.increment(processType);
        controller.saveProcess(processType, value);
    }
}
