package com.apress.progwt.client.gui.timeline;

import java.io.Serializable;
import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;

public class TimeLineObj<T> implements Serializable,
        Comparable<TimeLineObj<T>> {

    private static final long DIV = 60000;

    public static Date getDateFromViewPanelX(int viewPanelX) {
        long asLong = viewPanelX * DIV;
        return new Date(asLong);
    }

    public static int getLeftForDate(Date date) {
        long div = (date.getTime() / DIV);
        int left = (int) div;
        if (div != left) {
            Log.debug("TLO Fail" + (div == left) + "div " + div
                    + " left " + left);
        }
        return left;
    }

    private HasDate date;

    private T object;

    public TimeLineObj() {
    }

    public TimeLineObj(T object, HasDate date) {
        this.object = object;
        this.date = date;
    }

    public int compareTo(TimeLineObj<T> tl) {
        return getStartDate().compareTo(tl.getStartDate());
    }

    public Date getEndDate() {
        return date.getEndDate();
    }

    /**
     * 
     * @return
     */
    public HasDate getHasDate() {
        return date;
    }

    /**
     * convert time from seconds before 1970 to minutes. signed int goes
     * to 2,147,483,648 == ~68 Years in seconds doing it in minutes
     * instead gives us 1970 +/- 4085 years so 2115 BC in minute
     * precision. Probably enough for now.
     * 
     * @return
     */
    public int getLeft() {
        return getLeftForDate(getStartDate());
    }

    public T getObject() {
        return object;
    }

    public Date getStartDate() {
        return date.getStartDate();
    }

    public int getWidth() {
        if (getEndDate() == null) {
            return 10;
        } else {
            Log.debug("TLO.Width " + getLeftForDate(getEndDate()) + " "
                    + getLeftForDate(getStartDate()) + " End "
                    + getEndDate() + " start " + getStartDate());
            return getLeftForDate(getEndDate())
                    - getLeftForDate(getStartDate());
        }
    }

    /**
     * TODO instanceof
     * 
     * @param positionX
     */
    public Date setEndDateToX(int positionX) {

        // Log.debug("pos: " + positionX + " " +
        // getDateFromViewPanelX(positionX) );

        Date newD = getDateFromViewPanelX(positionX);
        if (newD.after(getStartDate())) {
            date.setEndDate(newD);
        }

        Log.debug("Eat endDateSet");

        // Log.debug("TLO: End Date = " + getEndDate());

        return date.getEndDate();

    }

    public Date setStartDateToX(int positionX) {

        // Log.debug("pos: " + positionX + " " +
        // getDateFromViewPanelX(positionX) + " ");

        Date newD = getDateFromViewPanelX(positionX);

        date.setStartDate(newD);

        if (null != getEndDate() && newD.after(getEndDate())) {
            date.setEndDate(newD);
        }

        // Log.debug("TLO: Start Date = " + getStartDate());

        return date.getStartDate();
    }

    public String toString() {
        return object + " " + getStartDate() + " " + getEndDate();
    }
}
