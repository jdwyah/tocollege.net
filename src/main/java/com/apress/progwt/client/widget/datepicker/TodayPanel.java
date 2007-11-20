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
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * TodayPanel extends AbsolyePanel and is extemely simple panel.
 * All it shows is a link which displays today's date in the 
 * textbox 
 */
public class TodayPanel extends SimplePanel implements ClickListener {

	private DatePicker datePicker;
	
	/*
	 * Default Constructor 
	 */
	public TodayPanel(DatePicker datePicker){
		this.datePicker = datePicker;
		init();
	}
	
	/*
	 * init
	 * 
	 * Does the initialization of the panel and all its
	 * children widgets 
	 */
	public void init(){
		Label todayLink = new Label("Today");		
		todayLink.addClickListener(this);
		this.add(todayLink);
		this.setWidth(100 + "%");
		this.setHeight(16+"px");
		this.setStyleName("todayPanel");		
	}
		
	/*
	 * onClick
	 * 
	 * Overridding method from ClickListener. Populates the
	 * textbox with the today's date. It uses DateFormatter
	 * Object to decide which format to be displayed.
	 */
	public void onClick(Widget sender) {
        //this.datePicker.setText(DateFormatter.formatDate(new Date(), DateFormatter.MMDDYYYY));
		
		//this.datePicker.setText(this.dateFormatter.formatDate(new Date()));
       
		datePicker.setSelectedDate(new Date());
		datePicker.setCurrentDate(new Date());
		datePicker.redrawCalendar();
		this.datePicker.hide();
	}

}
