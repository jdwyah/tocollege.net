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
package com.apress.progwt.client.exception;

import java.io.Serializable;

/**
 * Serialize message ourselves, because Exception is not Serializable and
 * thus breaks the GWT serializable chain, the super(message) will not be
 * serialized. This means we need to override getMessage() as well and
 * we'll need to be careful about using other Exception methods as well.
 * 
 * @author Jeff Dwyer
 * 
 */
public class SiteException extends Exception implements Serializable {

    private String message;

    public SiteException() {

    }

    public SiteException(String message) {
        super(message);
        this.message = message;
    }

    public SiteException(Exception e) {
        super(e);
        this.message = e.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return message;
    }

    /**
     * Overriden to return our individually serialized value since
     * Exception base class is not serializable.
     */
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }

}
