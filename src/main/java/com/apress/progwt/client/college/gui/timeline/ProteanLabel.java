package com.apress.progwt.client.college.gui.timeline;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

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
    public void setCenter(final Date d2, final int zoomIndex,
            final DateTimeFormat format) {

        Date newD = new Date(d2.getTime());

        switch (zoomIndex) {
        case 9:
            newD.setYear((newD.getYear() - (newD.getYear() % 100)) + idx
                    * 100);
            newD.setMonth(0);
            newD.setDate(1);
            break;
        case 8:
            newD.setYear((newD.getYear() - (newD.getYear() % 10)) + idx
                    * 10);
            newD.setMonth(0);
            newD.setDate(1);
            break;
        case 7:
            newD.setYear(newD.getYear() + idx);
            newD.setMonth(0);
            newD.setDate(1);
            break;
        case 6: // 3 years
            newD.setYear(newD.getYear() + idx);
            newD.setMonth(0);
            newD.setDate(1);
            break;
        case 5:// month
            newD.setYear(newD.getYear() + idx);
            newD.setMonth(0);
            newD.setDate(1);
            break;
        case 4:// 3 month
            newD.setMonth(newD.getMonth() + idx);
            newD.setDate(1);
            break;
        case 3:// week
            newD.setDate(1 + 7 * idx);
            newD.setHours(0);
            newD.setMinutes(0);
            break;
        case 2:
            newD.setDate(newD.getDate() + idx);
            newD.setHours(0);
            newD.setMinutes(0);
            break;
        case 1:
            newD.setHours(newD.getHours() + idx);
            newD.setMinutes(0);
            newD.setSeconds(0);
            break;
        case 0:
            newD.setDate(newD.getDate() + idx);// only show 1
            newD.setMinutes(newD.getMinutes() + idx);
            newD.setSeconds(0);
            break;
        default:
            break;
        }

        // Log.debug(zoomIndex+" "+idx+"d2 "+d2+" "+newD+"
        // "+TimeLineObj.getLeftForDate(newD));

        int llleft = TimeLineObj.getLeftForDate(newD);
        setLeft(llleft);
        setText(format.format(newD));

    }
}
