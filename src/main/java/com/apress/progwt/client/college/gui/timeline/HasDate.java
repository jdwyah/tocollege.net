package com.apress.progwt.client.college.gui.timeline;

import java.util.Date;

public interface HasDate {

    Date getEndDate();

    Date getStartDate();

    void setStartDate(Date newD);

    void setEndDate(Date newD);

    long getId();

    String getTitle();

    void setTitle(String text);

}
