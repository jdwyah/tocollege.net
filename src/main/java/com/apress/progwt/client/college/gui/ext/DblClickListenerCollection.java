package com.apress.progwt.client.college.gui.ext;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.user.client.ui.Widget;

public class DblClickListenerCollection extends ArrayList {


	/**
	 * Fires a click event to all listeners.
	 * 
	 * @param sender
	 *            the widget sending the event.
	 */
	public void fireDoubleClick(Widget sender) {
		for (Iterator it = iterator(); it.hasNext();) {
			DblClickListener listener = (DblClickListener) it.next();
			listener.onDblClick(sender);
		}
	}


}
