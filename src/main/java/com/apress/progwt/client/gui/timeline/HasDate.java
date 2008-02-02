package com.apress.progwt.client.gui.timeline;

import java.util.Date;

public interface HasDate {

    Date getEndDate();

    Date getStartDate();

    void setStartDate(Date newD);

    void setEndDate(Date newD);

    String getTitle();

}
