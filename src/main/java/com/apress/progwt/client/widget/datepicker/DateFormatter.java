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
import java.util.Date;

public class DateFormatter implements DateConstants {
	public static DateFormat DATE_FORMAT_MMDDYYYY = DateFormat.DATE_FORMAT_MMDDYYYY;
	public static DateFormat DATE_FORMAT_DDMMYYYY = DateFormat.DATE_FORMAT_DDMMYYYY;
	public static DateFormat DATE_FORMAT_DDMONYYYY = DateFormat.DATE_FORMAT_DDMONYYYY;
	
	private DateFormat dateFormat = null;
	
	public DateFormatter(DateFormat dateFormat){
		this.dateFormat = dateFormat;
	}

	/*
	 * formatDate
	 * 
	 * Formats the date in the format asked
	 * 
	 * @param Date (Date to be formatted)
	 * @param int (format in which to format the date)
	 * @return String
	 */
	public String formatDate (Date date){
		String dateStr = "";
		
		switch(this.dateFormat.getDateFormat()){
		case DateFormat.MMDDYYYY : 
			dateStr = formatDate_MMDDYYYY(date);
			break;
		
		case DateFormat.DDMMYYYY:
			dateStr = formatDate_DDMMYYYY(date);
			break;
			
		case DateFormat.DDMONYYYY:
			dateStr = formatDate_DDMONYYYY(date);
			break;
		
		}
		return dateStr;
	}
	
	/*
	 * getDateFormat
	 * 
	 * @return Returns the dateFormat
	 */
	public DateFormat getDateFormat() {
		return dateFormat;
	}

	/*
	 * setDateFormat
	 * 
	 * @param Sets the dateFormat
	 */
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	/*
	 * formatDate_MMDDYYYY
	 * 
	 * Formates the given date in MMDDYYYY format
	 * 
	 * @param (Date to be formatted)
	 * @return String
	 */
	private String formatDate_MMDDYYYY (Date date){
		StringBuffer dateStr = new StringBuffer();
		if(date.getMonth() < 9){
			dateStr.append("0");
		}
		dateStr.append((date.getMonth()+1));
		dateStr.append("/");
		
		if(date.getDate() < 10){
			dateStr.append("0");
		}
		dateStr.append(date.getDate());
		dateStr.append("/");
		
		dateStr.append((date.getYear()+1900));
		return dateStr.toString();
	}
	
	/*
	 * formatDate_DDMMYYYY
	 * 
	 * Formats the date in DDMMYYYY format
	 * 
	 * @param (Date to be formatted)
	 * @return String
	 */
	private String formatDate_DDMMYYYY (Date date){
		StringBuffer dateStr = new StringBuffer();
		if(date.getDate() < 10){
			dateStr.append("0");
		}
		dateStr.append(date.getDate());
		dateStr.append("/");
		
		if(date.getMonth() < 9){
			dateStr.append("0");
		}
		dateStr.append((date.getMonth()+1));
		dateStr.append("/");
		
		dateStr.append((date.getYear()+1900));
		return dateStr.toString();
	}	
	
	/*
	 * formatDate_DDMONYYYY
	 * 
	 * Formats the date in DDMONYYYY format
	 * 
	 * @param (Date to be formatted)
	 * @return String
	 */
	private String formatDate_DDMONYYYY(Date date){
		StringBuffer dateStr = new StringBuffer();
		if(date.getDate() < 10){
			dateStr.append("0");
		}
		dateStr.append(date.getDate());
		dateStr.append(" ");
		
		dateStr.append(MONTHS[date.getMonth()]);
		dateStr.append(" ");
		
		dateStr.append((date.getYear()+1900));
		return dateStr.toString();
	}		
}
