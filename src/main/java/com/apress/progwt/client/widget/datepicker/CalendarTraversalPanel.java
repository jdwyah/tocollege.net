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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

/*
 * CalendarTraversalPanel extends Dock Panel and comprises of
 * buttons which allow the users to traverse month and year in 
 * the calendar. It also shows the current month and year
 */
public class CalendarTraversalPanel extends DockPanel implements ClickListener{
	
	HTML monthYearDesc = new HTML();
	//Button prevMonth = new Button("&nbsp;&nbsp;&lt;&nbsp;&nbsp;", this);
	Button prevMonth = new Button("&lt;", this);
	Button prevYear = new Button("&lt;&lt;", this);
	Button nextYear = new Button("&gt;&gt;", this);
	Button nextMonth = new Button("&gt;", this);

	DatePicker datePicker = null;
	
	/*
	 * Constrcutor
	 * 
	 * @param DatePicker
	 */
	public CalendarTraversalPanel(DatePicker datePicker){
		this.datePicker = datePicker;
		init();
	}
	
	/*
	 * init
	 * 
	 * Does the initialization of the panel along with its
	 * children widgets
	 */
	private void init(){
		this.setHeight(18 +"px");
		drawTitle();
		
		this.prevMonth.setWidth(23 + "px");
		this.nextMonth.setWidth(23 + "px");
		
		DOM.setAttribute(prevMonth.getElement(), "title", "Previous Month");
		DOM.setAttribute(prevYear.getElement(), "title", "Previous Year");
		DOM.setAttribute(nextMonth.getElement(), "title", "Next Month");
		DOM.setAttribute(nextYear.getElement(), "title", "Next Year");
		
		HorizontalPanel prevButtons = new HorizontalPanel();
		prevMonth.setStyleName("monthYearTraversorBtn");
		prevYear.setStyleName("monthYearTraversorBtn");
		prevButtons.add(prevYear);
		prevButtons.add(prevMonth);
		
		HorizontalPanel nextButtons = new HorizontalPanel();
		nextMonth.setStyleName("monthYearTraversorBtn");
		nextYear.setStyleName("monthYearTraversorBtn");
		nextButtons.add(nextMonth);
		nextButtons.add(nextYear);
		
		this.add(prevButtons, DockPanel.WEST);
		this.setCellHorizontalAlignment(prevButtons, DockPanel.ALIGN_LEFT);
		this.add(nextButtons, DockPanel.EAST);
		this.setCellHorizontalAlignment(nextButtons, DockPanel.ALIGN_RIGHT);
		this.add(monthYearDesc, DockPanel.CENTER);
		this.setVerticalAlignment(DockPanel.ALIGN_MIDDLE);
		this.setCellHorizontalAlignment(this.monthYearDesc, HasAlignment.ALIGN_CENTER);
		this.setCellVerticalAlignment(this.monthYearDesc, HasAlignment.ALIGN_MIDDLE);
		this.setCellWidth(monthYearDesc, "100%");
		monthYearDesc.setStyleName("monthYearTraversor");
		
		this.setStyleName("monthYearTraversor");
	}
	
	/*
	 * drawTitle
	 * 
	 * Displays the current month and year
	 */
	void drawTitle(){
		redrawTitle();
	}
	
	/*
	 * redrawTitle
	 * 
	 * Displays the new month and year when the user traverses
	 * the month or year
	 */
	private void redrawTitle(){
		StringBuffer monthYearTitle = new StringBuffer();
		monthYearTitle.append(DateUtil.getMonth(this.datePicker.getCurrentDate()));
		monthYearTitle.append("-");
		monthYearTitle.append(this.datePicker.getCurrentDate().getYear()+1900);
		this.monthYearDesc.setText(monthYearTitle.toString());
	}
	
	/*
	 * onClick
	 * 
	 * Method from ClickListener
	 * Decribes what to do when the user clicks on the buttons
	 * on the panel
	 */
	public void onClick(Widget sender) {
		if (sender == prevMonth) {
			this.datePicker.setCurrentDate(DateUtil.addMonths(this.datePicker.getCurrentDate(), -1));
		} else if (sender == prevYear) {
			this.datePicker.setCurrentDate(DateUtil.addYears(this.datePicker.getCurrentDate(), -1));
		} else if (sender == nextYear) {
			this.datePicker.setCurrentDate(DateUtil.addYears(this.datePicker.getCurrentDate(), 1));
		} else if (sender == nextMonth) {
			this.datePicker.setCurrentDate(DateUtil.addMonths(this.datePicker.getCurrentDate(), 1));
		}
		this.redrawTitle();
		this.datePicker.redrawCalendar();
	}
}
