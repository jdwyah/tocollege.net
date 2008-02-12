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
/*
 * Simple Date Picker Widget for GWT library of Google, Inc.
 * 
 * Copyright (c) 2006 Parvinder Thapar
 * http://psthapar.googlepages.com/
 * 
 * This library is free software; you can redistribute 
 * it and/or modify it under the terms of the GNU Lesser 
 * General Public License as published by the Free Software 
 * Foundation; either version 2.1 of the License, or 
 * (at your option) any later version. This library is 
 * distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY  or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNULesser General Public License for more details. 
 * You should have received a copy of the GNU Lesser General 
 * PublicLicense along with this library; if not, write to the 
 * Free Software Foundation, Inc.,  
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA  
 */

package com.apress.progwt.client.widget.datepicker;

import java.util.Date;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;

/*
 * CalendarPanel extends AbsolutePanel and is the main panel which
 * displays the current month and its days. All the weekdays are
 * selectable items. Clicking on any weekday makes the selection and the
 * date is displayed in the textbox. All the weekends are grayed out and
 * doesnot allow selection
 */
public class CalendarPanel extends AbsolutePanel {

    DatePicker datePicker = null;
    FlexTable currentMonth = new FlexTable();
    String cellValues[][] = new String[7][7];

    // Holds whether or not the weekends be allowed to be selected
    private boolean isWeekendSelectable = false;

    // Holds the date format needed to be displayed
    private DateFormatter dateFormatter = new DateFormatter(
            DateFormatter.DATE_FORMAT_MMDDYYYY);

    /*
     * Constructor
     * 
     * @param DatePicker
     */
    public CalendarPanel(DatePicker datePicker) {
        this.datePicker = datePicker;
        init();
    }

    /*
     * init
     * 
     * Does the initialization of the panel and all its children widgets
     */
    private void init() {
        this.setHeight("100%");
        setPixelSize(168, 153);
        this.setStyleName("calendarPanel");
        currentMonth.addTableListener(new DateClickListener(this));
        this.add(this.drawCalendar());
    }

    /*
     * redrawCalendar
     * 
     * Shows new dates per the month or year selected by the user
     */
    public void redrawCalendar() {
        // Log.debug("REDRAWING");
        this.clearCalendar(this.currentMonth);

        // JD
        this.buildMonthBody(this.datePicker.getCurrentDate(),
                this.currentMonth);
        // this.buildMonthBody(this.datePicker.getSelectedDate(),
        // this.currentMonth);
    }

    /*
     * drawCalendar
     * 
     * Displays the dates for the current date
     */
    private Widget drawCalendar() {
        this.currentMonth.setStyleName("monthDates");
        this.buildMonthHeader(currentMonth);
        this.buildMonthBody(datePicker.getCurrentDate(), currentMonth);
        return currentMonth;
    }

    /*
     * clearCalendar
     * 
     * Clears out the dates in the month
     */
    protected void clearCalendar(FlexTable monthBody) {
        for (int row = 1; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                monthBody.setText(row, col, "");
            }
        }
    }

    /*
     * buildMonthHeader
     * 
     * Displays the header of the month with all the days of the week
     * spelled out
     */
    protected void buildMonthHeader(FlexTable monthHeader) {
        monthHeader.setHTML(0, 0,
                "<font class='monthHeaderHoliday'>Sun</font>");
        monthHeader.setHTML(0, 1, "<font class='monthHeader'>Mon</font>");
        monthHeader.setHTML(0, 2, "<font class='monthHeader'>Tue</font>");
        monthHeader.setHTML(0, 3, "<font class='monthHeader'>Wed</font>");
        monthHeader.setHTML(0, 4, "<font class='monthHeader'>Thu</font>");
        monthHeader.setHTML(0, 5, "<font class='monthHeader'>Fri</font>");
        monthHeader.setHTML(0, 6,
                "<font class='monthHeaderHoliday'>Sat</font>");

    }

    /*
     * buildMonthBody
     * 
     * Calculates the number of days in the current month and the first
     * start day. Then displays all the dates of that month. Weekdays are
     * made selectable while weekends are grayed out and cannot be
     * selected
     */
    protected void buildMonthBody(Date date, FlexTable monthBody) {
        int startWeekDay = DateUtil.getStartWeekDay(date);
        int numDays = DateUtil.getNumDaysInMonth(date.getMonth(),
                DateUtil.isLeapYear(date));
        int dayCount = 0;
        for (int row = 1; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                if (row == 1 && col < startWeekDay) {
                    monthBody.setText(row, col, "");
                    cellValues[row][col] = "";
                } else {
                    if (numDays > dayCount) {
                        int selectedDate = ++dayCount;
                        cellValues[row][col] = selectedDate + "";
                        if (col == 0 || col == 6) {
                            if (true == isSelectedDate(date, selectedDate)) {
                                monthBody.setHTML(row, col,
                                        "<font class='selectedDate'>"
                                                + selectedDate
                                                + "</font>");
                            } else {
                                if (this.isWeekendSelectable()) {
                                    monthBody.setHTML(row, col,
                                            "<font class='holidaySelectable'>"
                                                    + selectedDate
                                                    + "</font>");
                                } else {
                                    monthBody.setHTML(row, col,
                                            "<font class='holiday'>"
                                                    + selectedDate
                                                    + "</font>");
                                }
                            }
                        } else {
                            if (true == isSelectedDate(date, selectedDate)) {
                                monthBody
                                        .setHTML(row, col,
                                                "<font class='selectedDate'>"
                                                        + selectedDate
                                                        + "<font>");
                            } else {
                                monthBody
                                        .setHTML(row, col,
                                                "<font class='calendarDate'>"
                                                        + selectedDate
                                                        + "<font>");
                            }
                        }
                    } else {
                        monthBody.setText(row, col, "");
                        cellValues[row][col] = "";
                    }
                }
            }
        }
    }

    /*
     * DateClickListener is a Listener class which implementes the
     * TableListener.
     * 
     * The main panel uses FlexTable to display the dates of the month.
     * This Listener is for the whole Table
     */
    protected class DateClickListener implements TableListener {
        CalendarPanel calPanel;

        public DateClickListener(CalendarPanel calPanel) {
            this.calPanel = calPanel;
        }

        public void onCellClicked(SourcesTableEvents sender, int row,
                int col) {
            if (!calPanel.isWeekendSelectable() && (col == 0 || col == 6)) {
                return;
            }

            String cellValue = this.calPanel.cellValues[row][col];
            if (null == cellValue || "null".equals(cellValue)
                    || cellValue.trim().length() == 0) {
                return;
            }
            Date currentDate = this.calPanel.datePicker.getCurrentDate();
            /*
             * String dateStr = DateFormatter.formatDate( new Date(
             * currentDate.getYear(), currentDate.getMonth(),
             * Integer.parseInt(this.calPanel.cellValues[row][col]) ),
             * //DateFormatter.MMDDYYYY calPanel.getDateFormat() );
             */
            Date newDate = new Date(currentDate.getYear(), currentDate
                    .getMonth(), Integer
                    .parseInt(this.calPanel.cellValues[row][col]));

            this.calPanel.datePicker.setSelectedDate(newDate);
            // this.calPanel.datePicker.setText(dateStr);
            this.calPanel.datePicker.hide();
        }
    }

    /*
     * isSelecteDate
     * 
     * Checks whether or not the date being printed is the selected date
     * or current date
     */
    private boolean isSelectedDate(Date date, int printedDate) {
        boolean isCurrentDate = false;// pessimistic
        Date selectedDate = this.datePicker.getSelectedDate();
        if (selectedDate.getMonth() == date.getMonth()
                && selectedDate.getYear() == date.getYear()
                && selectedDate.getDate() == printedDate) {
            isCurrentDate = true;
        }
        return isCurrentDate;
    }

    /*
     * getWeekendSelectable Getter method for isWeekendSelectable
     * 
     * @return boolean
     */
    public boolean isWeekendSelectable() {
        return isWeekendSelectable;
    }

    /*
     * setWeekendSelectable Setter method for isWeekendSelectable
     * 
     * @param boolean
     */
    public void setWeekendSelectable(boolean isWeekendSelectable) {
        this.isWeekendSelectable = isWeekendSelectable;
    }

    /*
     * setDateFormatter Getter method for DateFormat
     * 
     * @return DateFormat
     */
    public DateFormatter getDateFormatter() {
        return dateFormatter;
    }

    /*
     * setDateFormatter Setter method for DateFormat
     * 
     * @param DateFormat
     */
    public void setDateFormatter(DateFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

}
