package com.apress.progwt.client.gui.timeline;

import java.util.Date;

/**
 * ProteanLabels are the marker labels that give us perspective. Depending
 * on the zoom level, we'll show different numbers of them and use
 * different dateformatters, since when we're at the millenial level, we
 * only want a label every century (1900,2000,2100) but when we're at the
 * month level, we want one every month (Jan,Feb,Mar)
 * 
 * See the recenter() code which is carefull not to run into the
 * compounding effects of leap years.
 * 
 * @author Jeff Dwyer
 * 
 */
public class ProteanLabel extends LabelWrapper {

    private int idx;

    public ProteanLabel(int idx, int top) {
        super("", 0, top);
        this.idx = idx;
    }

    public void setLeft(int left) {
        this.left = left;

    }

    /**
     * tell this label that there is a new center. We should update
     * ouselves. Our idx tells us how many 'units' away from the center we
     * should be.
     * 
     * NOTE, first attempt did 1/higherScale * idx, which seems like it
     * should work until you remember leap years cumulative effects of
     * months with different dates etc. Doing it that way it's too easy to
     * end up with Dec 29 when you wanted Jan 1
     * 
     * @param d2
     * @param currentScale
     * @param zoomIndex
     * @param format
     */
    public void setCenter(final Date d2, ZoomLevel zoomLevel) {

        Date newD = new Date(d2.getTime());

        newD = reCenter(newD, zoomLevel);

        // Log.debug(zoomIndex+" "+idx+"d2 "+d2+" "+newD+"
        // "+TimeLineObj.getLeftForDate(newD));

        int llleft = TimeLineObj.getLeftForDate(newD);
        setLeft(llleft);
        setText(zoomLevel.getDfFormat().format(newD));

    }

    /**
     * We have a spread of ProteanLabels
     * 
     * @param newD
     * @param idx
     * @return
     */
    public Date reCenter(Date newD, ZoomLevel zoomLevel) {

        if (zoomLevel == ZoomLevel.Centuries3) {
            newD.setYear((newD.getYear() - (newD.getYear() % 100)) + idx
                    * 100);
            newD.setMonth(0);
            newD.setDate(1);
        } else if (zoomLevel == ZoomLevel.Century) {
            newD.setYear((newD.getYear() - (newD.getYear() % 10)) + idx
                    * 10);
            newD.setMonth(0);
            newD.setDate(1);
        } else if (zoomLevel == ZoomLevel.Decade) {
            newD.setYear(newD.getYear() + idx);
            newD.setMonth(0);
            newD.setDate(1);
        }

        else if (zoomLevel == ZoomLevel.Years3) {
            newD.setYear(newD.getYear() + idx);
            newD.setMonth(0);
            newD.setDate(1);
        }

        else if (zoomLevel == ZoomLevel.Year) {
            newD.setYear(newD.getYear() + idx);
            newD.setMonth(0);
            newD.setDate(1);
        }

        else if (zoomLevel == ZoomLevel.Months3) {
            newD.setMonth(newD.getMonth() + idx);
            newD.setDate(1);
        }

        else if (zoomLevel == ZoomLevel.Month) {
            newD.setDate(1 + 7 * idx);
            newD.setHours(0);
            newD.setMinutes(0);
        }

        else if (zoomLevel == ZoomLevel.Week) {
            newD.setDate(newD.getDate() + idx);
            newD.setHours(0);
            newD.setMinutes(0);
        }

        else if (zoomLevel == ZoomLevel.Day) {
            newD.setHours(newD.getHours() + idx);
            newD.setMinutes(0);
            newD.setSeconds(0);
        } else if (zoomLevel == ZoomLevel.Hour) {
            newD.setDate(newD.getDate() + idx);// only show 1
            newD.setMinutes(newD.getMinutes() + idx);
            newD.setSeconds(0);
        }

        return newD;
    }
}
