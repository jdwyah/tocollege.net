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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;

/**
 * Use this class to get around varying implementations of:
 * 
 * DOM.eventGetClientX(evt);
 * 
 * Between Friefox and the rest of the world. Usage: call setLastXY() in a
 * mousemove listener.
 * 
 * @author Jeff Dwyer
 * 
 */
public class ClientMouseImpl {

    protected int x;
    protected int y;

    public void setLastXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getClientX(Event evt) {
        if (evt != null) {
            return DOM.eventGetClientX(evt);
        } else {
            return x;
        }
    }

    public int getClientY(Event evt) {
        if (evt != null) {
            return DOM.eventGetClientY(evt);
        } else {
            return y;
        }
    }
}
