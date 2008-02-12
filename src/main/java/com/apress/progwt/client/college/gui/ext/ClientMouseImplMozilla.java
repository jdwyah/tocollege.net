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
