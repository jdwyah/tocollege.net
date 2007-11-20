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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/*
 * SimpleDatePicker is a date picker which sub classes DatePicker and defines a calendar popup which
 * allows user to traverse back and forth in month and year
 * 
 * 
 * 
 * WARN! NOTE ETC!!!!! DON'T Use any date format besides mm/dd/yyyy !!
 */
public class SimpleDatePicker extends DatePicker implements ClickListener {

	protected PopupPanel calendarPopup = new PopupPanel(true);
	protected VerticalPanel vertPanel = new VerticalPanel();
	protected CalendarPanel calendarPanel = null;
	protected TodayPanel todayPanel = null;
	protected CalendarTraversalPanel calendarTraversalPanel = null;

	/*
	 * Default Constructor
	 */
	public SimpleDatePicker() {
		super();
		this.init();
		this.calendarPanel = new CalendarPanel(this);
		this.calendarTraversalPanel = new CalendarTraversalPanel(this);
		this.todayPanel = new TodayPanel(this);
		this.addCalendar(this);
	}

	/*
	 * Overridden Constructor
	 * 
	 * @param String
	 * 
	 * Uses the name to assign to the "name" value of the HTML textbox
	 */
	public SimpleDatePicker(String name) {
		this();
		DOM.setAttribute(this.getElement(), "name", name);
	}

	/*
	 * init
	 * 
	 * Does the initialization to the textbox
	 */
	protected void init() {
		this.setWidth(80 + "px");
		this.setStyleName("txtbox");
		addClickListener(this);
		addKeyboardListener(this);
	}

	/*
	 * addCalendar
	 * 
	 * Adds the popup panel which displays three main panels: a. CalendarTraversalPanel: DockPanel
	 * b. CalendarPanel: AbsolutePanel c. TodayPanel: AbsolutePanel
	 * 
	 * @param DatePicker
	 */
	protected void addCalendar(DatePicker datePicker) {
		vertPanel.add(calendarTraversalPanel);
		vertPanel.add(calendarPanel);
		vertPanel.add(todayPanel);
		vertPanel.addStyleName("date_DisplayPanel");
		calendarPanel.addStyleName("date_popupPanel");
		calendarPopup.add(vertPanel);
	}

	/*
	 * showCalendar
	 * 
	 * Displays the popup calendar panel
	 */
	protected void showCalendar() {
		calendarPopup.show();
		calendarPopup.setPopupPosition(this.getAbsoluteLeft(), this.getAbsoluteTop()
				+ this.getOffsetHeight() + 4);
		calendarPopup.setHeight(120 + "px");
		calendarPopup.setWidth(165 + "px");
	}

	/*
	 * hideCalendar
	 * 
	 * Hides the popup calendar panel
	 */
	public void hideCalendar() {
		this.calendarPopup.hide();
	}

	/*
	 * redrawCalendar
	 * 
	 * Redraws the calendar panel with new month/ year
	 */
	public void redrawCalendar() {
		this.calendarPanel.redrawCalendar();
		this.calendarTraversalPanel.drawTitle();
	}

	/*
	 * Methods from AbstractUIDatePicker
	 * 
	 */
	public void show() {
		this.redrawCalendar();
		this.showCalendar();
	}

	public void hide() {
		this.hideCalendar();
	}

	/*
	 * Methods from ClickListener
	 */
	public void onClick(Widget sender) {
		// System.out.println("ONCLICK!!!!!!!!!!!! ");
		// System.out.println("sel "+getSelectedDate()+" || "+this.getText()+" ||
		// "+getCurrentDate());
		//		
		// System.out.println("STRING: "+this.getText());
		Date ddd = DateUtil.convertString2Date(this.getText());
		// System.out.println("ddd "+ddd);
		this.setSelectedDate(ddd);
		// System.out.println("b4show "+getSelectedDate()+" "+this.getText()+" "+getCurrentDate());
		this.show();
	}

	/*
	 * getWeekendSelectable Getter method for isWeekendSelectable
	 * 
	 * @return boolean
	 */
	public boolean isWeekendSelectable() {
		return this.calendarPanel.isWeekendSelectable();
	}

	/*
	 * setWeekendSelectable Setter method for isWeekendSelectable
	 * 
	 * @param boolean
	 */
	public void setWeekendSelectable(boolean isWeekendSelectable) {
		this.calendarPanel.setWeekendSelectable(isWeekendSelectable);
	}

	/*
	 * setDateFormat Setter method set the format of the display date
	 * 
	 * @param boolean
	 */
	public void setDateFormat(DateFormat dateFormat) {
		super.setDateFormat(dateFormat);
		this.calendarPanel.setDateFormatter(this.getDateFormatter());
	}

}