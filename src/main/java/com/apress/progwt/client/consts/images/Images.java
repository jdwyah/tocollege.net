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
package com.apress.progwt.client.consts.images;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 * 
 * Note the (at)gwt.resource comments aren't required if files are named
 * like the method name;
 * 
 * @author Jeff Dwyer
 * 
 */
public interface Images extends ImageBundle {

    /**
     * @gwt.resource magnifyingBig.png
     */
    AbstractImagePrototype magnifyingBig();

    /**
     * @gwt.resource magnifyingSmall.png
     */
    AbstractImagePrototype magnifyingSmall();

    /**
     * @gwt.resource bullet_blue.png
     */
    AbstractImagePrototype bullet_blue();

    /**
     * @gwt.resource applying.png
     */
    AbstractImagePrototype applying();

    /**
     * @gwt.resource accepted.png
     */
    AbstractImagePrototype accepted();

    /**
     * @gwt.resource rejected.png
     */
    AbstractImagePrototype rejected();

    /**
     * @gwt.resource applied.png
     */
    AbstractImagePrototype applied();

    /**
     * @gwt.resource considering.png
     */
    AbstractImagePrototype considering();

    /**
     * @gwt.resource pctStatus0.png
     */
    AbstractImagePrototype pctStatus0();

    /**
     * @gwt.resource pctStatus25.png
     */
    AbstractImagePrototype pctStatus25();

    /**
     * @gwt.resource pctStatus50.png
     */
    AbstractImagePrototype pctStatus50();

    /**
     * @gwt.resource pctStatus75.png
     */
    AbstractImagePrototype pctStatus75();

    /**
     * @gwt.resource pctStatus100.png
     */
    AbstractImagePrototype pctStatus100();

    /**
     * @gwt.resource checked.png
     */
    AbstractImagePrototype checked();

    /**
     * @gwt.resource unchecked.png
     */
    AbstractImagePrototype unchecked();
}
