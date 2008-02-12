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
package com.apress.progwt.client.rpc;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Intended for local callbacks.
 * 
 * Won't put anything in the status window.
 * 
 * @author Jeff Dwyer
 * 
 */
public abstract class EZCallback<T> implements AsyncCallback<T> {
    public void onFailure(Throwable caught) {
        Log.warn("EZCall failed! " + caught + " " + caught.getMessage());
    }

}
