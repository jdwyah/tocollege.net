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
package com.apress.progwt.client.widget.datepicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * Make an easier way to pick dates in the distant past
 * 
 * @author Jeff Dwyer
 * 
 */
public class LongRangePicker extends Composite implements TableListener {

    private static final String SELECTED = "selectedDate";

    private static final int MONTH_ROW = 0;
    private static final int YEAR_ROW = 1;
    private static final int DECADE_ROW = 2;
    private static final int CENTURY_ROW = 3;

    private DatePicker datePicker;
    private FlexTable range = new FlexTable();
    private List ranges;

    private Date currentDate;

    public LongRangePicker(DatePicker datePicker) {
        this.datePicker = datePicker;

        currentDate = new Date();

        ranges = new ArrayList();

        for (int i = 0; i < 12; i++) {
            RangeLabel r = new MonthLabel(i);
            ranges.add(r);
            range.setWidget(MONTH_ROW, i, r);
        }

        doYearRow(YEAR_ROW, 10);
        doYearRow(DECADE_ROW, 100);
        doYearRow(CENTURY_ROW, 1000);

        range.addTableListener(this);
        range.setStyleName("longRange");

        // setAllTo(new Date());

        initWidget(range);
        addStyleName("longRange");
    }

    private void doYearRow(int row, int span) {
        range.setWidget(row, 0, new Clicker("<", -span));
        for (int i = 0; i < 10; i++) {
            RangeLabel r = new YearLabel(i, span);
            ranges.add(r);
            range.setWidget(row, i + 1, r);
        }
        range.setWidget(row, 11, new Clicker(">", span));
    }

    public void redraw(Date date) {
        setAllTo(date);
    }

    private void setAllTo(Date date) {
        for (Iterator iter = ranges.iterator(); iter.hasNext();) {
            RangeLabel label = (RangeLabel) iter.next();
            label.setToDate(date);
        }
    }

    private void userPicks(Date date) {
        datePicker.setCurrentDate(date);
        datePicker.redrawCalendar();
    }

    public void onCellClicked(SourcesTableEvents sender, int row, int col) {

        // Date currentDate = datePicker.getCurrentDate();
        //		
        // Date newDate = new Date( currentDate.getYear(),
        // currentDate.getMonth(),
        // 1900);

        if (range.getWidget(row, col) instanceof Clicker) {

        } else {
            RangeLabel rangeL = (RangeLabel) range.getWidget(row, col);
            userPicks(rangeL.getDate());
        }

        // Integer.parseInt(this.calPanel.cellValues[row][col])

        // datePicker.setSelectedDate(newDate);
        // datePicker.hide();

    }

    private int getYear(Date d) {
        return d.getYear() + 1900;
    }

    private abstract class RangeLabel extends Label {
        protected int i;

        public RangeLabel(int i) {
            this.i = i;
        }

        abstract public void setToDate(Date d);

        abstract public Date getDate();

    }

    private class MonthLabel extends RangeLabel {

        public MonthLabel(int i) {
            super(i);
            setText(DateConstants.MONTHS[i]);
        }

        public void setToDate(Date d) {
            if (d.getMonth() == i) {
                addStyleName(SELECTED);
            } else {
                removeStyleName(SELECTED);
            }
        }

        public Date getDate() {
            currentDate.setMonth(i);
            return currentDate;
        }
    }

    private class YearLabel extends RangeLabel {
        private int span;
        private int skew;
        private int myValue;

        public YearLabel(int i, int span) {
            this(i, span, 0);
        }

        public YearLabel(int i, int span, int skew) {
            super(i);
            this.span = span;
            this.skew = skew;
        }

        public void setToDate(Date d) {

            int intervalStart = (getYear(d) / span) * span;// int
                                                            // division
                                                            // floors
            int selected = (getYear(d) / (span / 10)) * (span / 10);

            // Log.debug("getYear(d) "+getYear(d)+" ints "+intervalStart+"
            // sel "+selected);

            myValue = (intervalStart + i * (span / 10)) + skew;

            setText("" + myValue);
            if (selected == myValue) {
                addStyleName(SELECTED);
            } else {
                removeStyleName(SELECTED);
            }
        }

        public Date getDate() {
            currentDate.setYear(myValue - 1900);
            return currentDate;
        }
    }

    private class Clicker extends Label implements ClickListener {

        private int span;

        public Clicker(String string, int span) {
            super(string);
            this.span = span;
            addClickListener(this);
        }

        /**
         * move the range down one section. don't just subtract though, or
         * 2000 - 10 = 1900 we'd prefer 2000 - 10 to be 1990.
         */
        public void onClick(Widget sender) {
            int intervalStart = (getYear(currentDate) / span) * span;
            if (span > 1) {
                currentDate.setYear(intervalStart + span - 1900);
            } else {
                // ie for decade, span == -100
                // so for 1950, interval start == 1900, then + (-100/10) =
                // 1890
                currentDate.setYear(intervalStart + (span / 10) - 1900);
            }
            userPicks(currentDate);
        }

    }

}
