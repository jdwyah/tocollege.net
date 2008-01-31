package com.apress.progwt.client.widget.datepicker;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

/**
 * Picker that includes a LongRangePicker for easier picking of dates in
 * the past.
 * 
 * @author Jeff Dwyer
 * 
 */
public class HDatePicker extends SimpleDatePicker implements
        DatePickerInterface {

    private static final int WIDTH = 530;

    private LongRangePicker longRangePicker;
    private HorizontalAlignmentConstant align;

    public HDatePicker(HorizontalAlignmentConstant constant) {
        super();
        this.align = constant;
    }

    protected void init() {
        this.setWidth(80 + "px");
        this.setStyleName("txtbox");
        addClickListener(this);
        addKeyboardListener(this);

        longRangePicker = new LongRangePicker(this);
    }

    protected void addCalendar(DatePicker datePicker) {
        vertPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        vertPanel.add(calendarTraversalPanel);
        vertPanel.add(calendarPanel);
        vertPanel.add(todayPanel);
        vertPanel.add(longRangePicker);
        // calendarPopup.addStyleName("date_DisplayPanel");
        vertPanel.setStyleName("date_DisplayPanel");
        calendarPopup.add(vertPanel);
    }

    // @Override
    public void redrawCalendar() {
        // Log.debug("H Date Picker redraw "+getSelectedDate());
        this.calendarPanel.redrawCalendar();
        this.calendarTraversalPanel.drawTitle();
        longRangePicker.redraw(getCurrentDate());
    }

    // @Override
    protected void showCalendar() {
        calendarPopup.show();
        if (align == HorizontalPanel.ALIGN_RIGHT) {

            // getAbsoluteLeft() is broken in FF for things in our
            // gadgetDisplayer
            // seems to be a known problem, but no good fix. Even FireBug
            // says that the
            // left position is 450px, which is totally wrong.
            //
            // found native method below on forum, but it returns the same
            // thing
            //
            // int left = this.getAbsoluteLeft() - WIDTH;
            int left = Window.getClientWidth() - WIDTH;

            // make sure it doesn't go too low
            int top = this.getAbsoluteTop() + this.getOffsetHeight() + 4;
            top = (Window.getClientHeight() - top > 400) ? top : Window
                    .getClientHeight() - 400;

            calendarPopup.setPopupPosition(left, top);
            Log
                    .debug("SHOW RIGHT "
                            + (this.getAbsoluteLeft() - WIDTH)
                            + " "
                            + (this.getAbsoluteTop()
                                    + this.getOffsetHeight() + 4));

            // Logger.log("SHOW RIGHT "+getAbsoluteLeft(getElement())+"
            // FIX "+getAbsoluteLeftFix(getElement()));

            calendarPopup.setHeight(120 + "px");
            calendarPopup.setWidth(165 + "px");
            calendarPopup.setStyleName("date_popupPanel");
        } else {
            calendarPopup.setPopupPosition(this.getAbsoluteLeft(), this
                    .getAbsoluteTop()
                    + this.getOffsetHeight() + 4);
            calendarPopup.setHeight(120 + "px");
            calendarPopup.setWidth(165 + "px");
            calendarPopup.setStyleName("date_popupPanel");
        }
    }

    // public native int getAbsoluteLeft(Element elem) /*-{
    // var left = 0;
    // while (elem) {
    // left += elem.offsetLeft - elem.scrollLeft;
    // elem = elem.offsetParent;
    // }
    // return left + $doc.body.scrollLeft;
    // }-*/;
    // public native int getAbsoluteLeftFix(Element elem) /*-{
    // var left = 0, parent;
    // while (elem) {
    // left += elem.offsetLeft - elem.scrollLeft;
    // parent =
    // @com.google.gwt.user.client.DOM::getParent(Lcom/google/gwt/user/client/Element;)(elem);
    // elem = elem.offsetParent;
    // while (parent != elem) {
    // left -= parent.scrollLeft;
    // parent =
    // @com.google.gwt.user.client.DOM::getParent(Lcom/google/gwt/user/client/Element;)(parent);
    // }
    // }
    // return left + $doc.body.scrollLeft;
    // }-*/;

    public Widget getWidget() {
        return this;
    }
}
