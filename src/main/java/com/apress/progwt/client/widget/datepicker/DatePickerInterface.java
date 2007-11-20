package com.apress.progwt.client.widget.datepicker;

import java.util.Date;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;

public interface DatePickerInterface {

	Widget getWidget();

	void setSelectedDate(Date created);

	Date getCurrentDate();

	Date getSelectedDate();

	void addChangeListener(ChangeListener changeListener);

	void setDateFormat(DateFormat date_format_mmddyyyy);

	void setWeekendSelectable(boolean b);

	DateFormatter getDateFormatter();

}
