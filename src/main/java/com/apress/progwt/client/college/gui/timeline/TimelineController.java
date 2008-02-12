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
package com.apress.progwt.client.college.gui.timeline;

import java.util.Date;

import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.ProcessType;
import com.apress.progwt.client.domain.ProcessValue;
import com.apress.progwt.client.gui.timeline.TimeLineObj;
import com.apress.progwt.client.gui.timeline.TimeLineObjFactory;

public interface TimelineController {

    void addProcess(Date date, int x, int y);

    void addProcess(ProcessType processType, Date date);

    Application getCurrentApplication();

    void setCurrentApplication(Application currentApplication);

    TimeLineObjFactory getTimeLineObjFactory();

    void setSelected(TimeLineObj<?> timeLineObj);

    void onTLOChange(TimeLineObj<?> tlo);

    void saveProcess(ProcessType processType, ProcessValue value);

}
