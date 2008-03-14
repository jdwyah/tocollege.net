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

    AbstractImagePrototype magnifyingBig();

    AbstractImagePrototype magnifyingSmall();

    AbstractImagePrototype bullet_blue();

    AbstractImagePrototype applying();

    AbstractImagePrototype accepted();
    
    AbstractImagePrototype rejected();
    
    AbstractImagePrototype applied();

    AbstractImagePrototype considering();
  
    AbstractImagePrototype pctStatus0();

    AbstractImagePrototype pctStatus25();

    AbstractImagePrototype pctStatus50();

    AbstractImagePrototype pctStatus75();

    AbstractImagePrototype pctStatus100();

    AbstractImagePrototype checked();

    AbstractImagePrototype unchecked();
}
