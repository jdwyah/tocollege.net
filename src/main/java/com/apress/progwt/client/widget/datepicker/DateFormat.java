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

/*
 * DateFormatter, as its name suggests, is the object which
 * formats the date in a particular way. Currently, it supports
 * 3 different formats: MMDDYYYY, DDMMYYYY and DDMONYYYY
 */

public class DateFormat {
	public static final int MMDDYYYY = 0; // e.g. 01/31/2006
	public static final int DDMMYYYY = 1; // e.g. 31/01/2006
	public static final int DDMONYYYY = 2; // e.g. 01 Jan 2006
	
	public static DateFormat DATE_FORMAT_MMDDYYYY = new DateFormat(0);
	public static DateFormat DATE_FORMAT_DDMMYYYY = new DateFormat(1);
	public static DateFormat DATE_FORMAT_DDMONYYYY = new DateFormat(2);
	
	private int dateFormat = 0;
	
	private DateFormat(int dateformat){
		this.dateFormat = dateformat;
	}
	
	public int getDateFormat(){
		return this.dateFormat;
	}
}
