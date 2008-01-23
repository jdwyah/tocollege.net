package com.apress.progwt.client.college.gui.ext;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Event;

/**
 * DOM.getClientX() is broken in FF for WHEEL events
 * https://bugzilla.mozilla.org/show_bug.cgi?id=352179
 * 
 * @author Jeff Dwyer
 * 
 */
public class ClientMouseImplMozilla extends ClientMouseImpl {

    public int getClientX(Event evt) {
        Log.debug("\n\nUSING MOZILLA");
        return x;
    }

    public int getClientY(Event evt) {
        return y;
    }
}
