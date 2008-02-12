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
 * See the GNU Lesser General Public License for more details. 
 * You should have received a copy of the GNU Lesser General 
 * PublicLicense along with this library; if not, write to the 
 * Free Software Foundation, Inc.,  
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA  
 */

package com.apress.progwt.client.widget.datepicker;

import java.util.Date;

/*
 * DateUtil class is a utility class which provides
 * date-related utility functions
 */
public class DateUtil implements DateConstants {

	/*
	 * getStartWeekDay
	 * 
	 * Checks and returns the first weekday of the selected month
	 * 
	 * @param Date
	 * @return int
	 */
	public static int getStartWeekDay(Date date){
		Date dateForFirstOfThisMonth = new Date(date.getYear(), date.getMonth(), 1);
		return dateForFirstOfThisMonth.getDay();
	}

	/*
	 * getNumDaysInMonth
	 * 
	 * Calculates and returns max days in a selected month
	 * 
	 * @param int (month)
	 * @param boolean
	 * @return int
	 */
	public static int getNumDaysInMonth(int month, boolean isLeapYear){
		int numDaysInMonth = maxDaysinMonth[month]; 
		if(1 == month){
			if (true == isLeapYear){
				numDaysInMonth = 29; 
			}else{
				numDaysInMonth = 28;
			}
		}
		return numDaysInMonth;
	}
	
	/*
	 * isLeapYear
	 * 
	 * Calculates and returns whether or not the
	 * current selected year is a Leap Year
	 * 
	 * @param Date
	 * @return boolean
	 */
	public static boolean isLeapYear (Date date){
		boolean isLeapYear = false;
		// Instantiate the date for 1st March of that year  
		Date firstMarch = new Date (date.getYear(), 2, 1);

		// Go back 1 day
		long firstMarchTime = firstMarch.getTime();
		long lastDayTimeFeb = firstMarchTime - NUM_MILLISECS_A_DAY;
		
		//Instantiate new Date with this time
		Date febLastDay = new Date (lastDayTimeFeb);
		
		// Check for date in this new instance
		isLeapYear = (29 == febLastDay.getDate()) ? true : false; 
		
		return isLeapYear;
	}
	
	/*
	 * addYears
	 *
	 * Adds the specfied number of years to the specified date
	 * and returns the new Date.
	 * 
	 * @param Date
	 * @param int
	 * @return Date
	 */
	public static Date addYears(Date date, int numYears){
		return new Date (date.getYear()+numYears, date.getMonth(), date.getDate());
	}	
	
	/*
	 * addMonths
	 *
	 * Adds the specfied number of months to the specified date
	 * and returns the new Date.
	 * 
	 * @param Date
	 * @param int
	 * @return Date
	 */
	public static Date addMonths(Date date, int numMonthsFromDate){
		int newMonth = date.getMonth()+numMonthsFromDate;
		int year = date.getYear();
		if(newMonth > 11){
			newMonth=0;
			year++;
		}
		return new Date (year, newMonth, date.getDate());
	}
	
	/*
	 * today
	 * 
	 * Returns current system date
	 * 
	 * @return Date
	 */
	public static Date today(){
		return new Date();
	}
	
	/*
	 * convertString2Date
	 * 
	 * Converts the MMDDYYY String to Date
	 * Currently only supports MMDDYYYY
	 * 
	 * @param String
	 * @return Date
	 */
	public static Date convertString2Date(String dateStr){
		if(dateStr.trim().length() == 0){
			return new Date();
		}
		
		int monthSep = dateStr.indexOf("/");
		if(-1 == monthSep){
			// Date is not properly formatted. 
			// Return the today date
			return new Date();
		}
		String month = dateStr.substring(0, monthSep);
		int nMonth = Integer.parseInt(month);
		if(nMonth > 12){
			// Date is not properly formatted. 
			// Return the today date
			return new Date();
		}
		
		String dateAndYear = dateStr.substring(monthSep+1, dateStr.length());
		
		int dateSep = dateAndYear.indexOf("/");
		if(-1 == dateSep){
			// Date is not properly formatted. 
			// Return the today date
			return new Date();
		}
		String date = dateAndYear.substring(0, dateSep);
		int nDate = Integer.parseInt(date);
		
		String year = dateAndYear.substring(dateSep+1, dateAndYear.length());
		int nYear = Integer.parseInt(year);
		
		return new Date(nYear-1900, nMonth-1, nDate);
	}
	
	/*
	 * getMonth
	 * 
	 * Returns the month name in the date
	 * 
	 * @param Date
	 * @return String
	 */
	public static String getMonth(Date date){
		return MONTHS[date.getMonth()];
	}
	public static String getMonth(int date){
		return MONTHS[date];
	}
}
