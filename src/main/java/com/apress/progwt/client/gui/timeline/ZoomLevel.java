package com.apress.progwt.client.gui.timeline;

import com.google.gwt.i18n.client.DateTimeFormat;

class Times {

    public static final double MIN_HOUR = 60;
    public static final double MIN_DAY = MIN_HOUR * 24;
    public static final double MIN_YEAR = MIN_DAY * 365.25;
    public static final double MIN_CENTURY = MIN_YEAR * 100;
    public static final double MIN_3CENTURY = MIN_CENTURY * 3;
    public static final double MIN_3MONTH = MIN_DAY * 91.31;
    public static final double MIN_3YEAR = MIN_YEAR * 3;

    public static final double MIN_DECADE = MIN_YEAR * 10;

    public static final double MIN_MILL = MIN_YEAR * 1000;
    public static final double MIN_MONTH = MIN_DAY * 30.43;
    public static final double MIN_WEEK = MIN_DAY * 7;
}

/**
 * Note that there are not unique css names for each zoom level. Also note
 * that they appear offset a little. This is intended.
 * 
 * @author Jeff Dwyer
 * 
 */
public enum ZoomLevel {

    Hour(Times.MIN_HOUR, "tl_hour", DateTimeFormat.getFormat("HH:mm")),

    Day(Times.MIN_DAY, "tl_hour", DateTimeFormat.getFormat("HH")),

    Week(Times.MIN_WEEK, "tl_day", DateTimeFormat.getFormat("MMM d")),

    Month(Times.MIN_MONTH, "tl_week", DateTimeFormat
            .getFormat("MMM, d yyyy")),

    Months3(Times.MIN_3MONTH, "tl_3way", DateTimeFormat
            .getFormat("MMMM yyyy")),

    Year(Times.MIN_YEAR, "tl_month", DateTimeFormat.getFormat("yyyy")),

    Years3(Times.MIN_3YEAR, "tl_3way", DateTimeFormat.getFormat("yyyy")),

    Decade(Times.MIN_DECADE, "tl_year", DateTimeFormat.getFormat("yyyy")),

    Century(Times.MIN_CENTURY, "tl_decade", DateTimeFormat
            .getFormat("yyyy")),

    Centuries3(Times.MIN_3CENTURY, "tl_3century", DateTimeFormat
            .getFormat("yyyy")),

    Millenium(Times.MIN_MILL, "tl_3century", DateTimeFormat
            .getFormat("yyyy"));

    private double timespan;
    private double scale;
    private String cssClass;
    private DateTimeFormat dfFormat;

    private ZoomLevel(double timespan, String cssClass,
            DateTimeFormat dfFormat) {
        this.timespan = timespan;
        this.cssClass = cssClass;
        this.dfFormat = dfFormat;
        this.scale = 1.0 / timespan;
    }

    public double getTimespan() {
        return timespan;
    }

    public String getCssClass() {
        return cssClass;
    }

    public DateTimeFormat getDfFormat() {
        return dfFormat;
    }

    public double getScale() {
        return scale;
    }

    public static ZoomLevel getZoomForScale(double scale) {
        for (ZoomLevel zl : ZoomLevel.values()) {
            if (zl.getScale() == scale) {
                return zl;
            }
        }
        return null;
    }

    public static ZoomLevel zoomOutOneFrom(ZoomLevel cur) {
        boolean next = false;
        for (ZoomLevel zl : ZoomLevel.values()) {
            // don't let us zoom all the way to millenia
            if (next || zl == Centuries3) {
                return zl;
            }
            if (zl == cur) {
                next = true;
            }
        }
        return null;
    }

    public static ZoomLevel zoomInOneFrom(ZoomLevel cur) {
        ZoomLevel prev = null;
        for (ZoomLevel zl : ZoomLevel.values()) {
            if (zl == cur) {
                return prev;
            }
            prev = zl;
        }
        return null;
    }

}
