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

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/*
 * Abstract Base class for a Date Picker object. Extend this class if you wish to create a new kind
 * of a date picker
 * 
 * Date Picker extends a Text box and is intended to be used in conjuction with the calendar popup.
 * It implements KeyBoardListener but the methods do nothing. You can implement its methids in your
 * sub class if needed.
 */
public abstract class DatePicker extends TextBox implements KeyboardListener {
	// Current maintains where the calendar is at any point in time
	// Use can be traversing back and forth in the calendar. This
	// field tracks that.
	private Date currentDate = new Date();

	// Keeps the collection of all the change listners
	private ChangeListenerCollection changeListeners;

	// Holds the date last selected by the user
	private Date selectedDate = new Date();

	// Holds the default DateFormatter Object
	private DateFormatter dateFormatter = new DateFormatter(DateFormatter.DATE_FORMAT_MMDDYYYY);

	/*
	 * Default Constructor
	 */
	protected DatePicker() {
		super();
	}

	/*
	 * show Abstract method to be implemented by all the sub classes. This tells the app how to show
	 * the date picker
	 */
	public abstract void show();

	/*
	 * hide Abstract method to be implemented by all the sub classes. The tells what to do when the
	 * calendar needs to be hidden
	 */
	public abstract void hide();

	/*
	 * redrawCalendar Abstract method to define what to do in case the user traverses the month or
	 * year
	 */
	public abstract void redrawCalendar();

	/*
	 * getCurrentDate Getter method for currentDate
	 * 
	 * @return Date
	 */
	public Date getCurrentDate() {
		return currentDate;
	}

	/*
	 * setCurrentDate Setter method for currentDate
	 * 
	 * You should probably call setSelectedDate() instead of this
	 * 
	 * @param Date
	 */
	protected void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	/*
	 * getSelectedDate Getter method for selectedDate
	 * 
	 * @return Date
	 */
	public Date getSelectedDate() {
		return selectedDate;
	}

	/*
	 * setSelectedDate Setter method for selectedDate
	 * 
	 * @param Date
	 */
	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
		setCurrentDate(selectedDate);
		String dateStr = dateFormatter.formatDate(selectedDate);
		setText(dateStr);
	}

	/*
	 * getWeekendSelectable Getter method for isWeekendSelectable
	 * 
	 * @return boolean
	 */
	public abstract boolean isWeekendSelectable();

	/*
	 * setWeekendSelectable Setter method for isWeekendSelectable
	 * 
	 * @param boolean
	 */
	public abstract void setWeekendSelectable(boolean isWeekendSelectable);

	/*
	 * getWeekendSelectable Getter method for isWeekendSelectable
	 * 
	 * @return boolean
	 */
	public DateFormatter getDateFormatter() {
		return this.dateFormatter;
	}

	/*
	 * setText
	 * 
	 * @param text to be displayed (String)
	 */
	public void setText(String text) {
		super.setText(text);
		if (changeListeners != null) {
			changeListeners.fireChange(this);
		}
	}

	/*
	 * setDateFormat Setter method set the format of the display date
	 * 
	 * @param boolean
	 */
	public void setDateFormat(DateFormat dateFormat) {
		this.getDateFormatter().setDateFormat(dateFormat);
	}

	/*
	 * Methods from KeyboardListener
	 */
	/**
	 * Not used at all
	 */
	public void onKeyDown(Widget sender, char keyCode, int modifiers) {
		this.show();
	}

	/**
	 * Not used at all
	 */
	public void onKeyPress(Widget sender, char keyCode, int modifiers) {
	}

	/**
	 * Not used at all
	 */
	public void onKeyUp(Widget sender, char keyCode, int modifiers) {

	}

	/*
	 * addChangeListener
	 * 
	 * Adds the Change Listener Object to its collection
	 */
	public void addChangeListener(ChangeListener listener) {
		if (null == this.changeListeners) {
			this.changeListeners = new ChangeListenerCollection();
		}
		this.changeListeners.add(listener);
	}
}
