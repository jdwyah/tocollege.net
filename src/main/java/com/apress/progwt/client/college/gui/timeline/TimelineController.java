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
