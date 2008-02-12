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
package com.apress.progwt.client.consts;

import com.google.gwt.libideas.resources.client.DataResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.TextResource;

/**
 * 
 * Resources live in /src/main/resources/com/apress/progwt/client/consts/
 * 
 * NOTE: Putting images in sub-directories is bad idea, since the / or \
 * needed to reference the subdirectory isn't platform independent.
 * 
 * In general we'll use the Images ImageBundle class for images. These
 * images need to be inserted directly into CSS files though, sinec we're
 * using them as background-images.
 * 
 * Our onModuleLoad() code will need to call:
 * 
 * ConstHolder.resources = (Resources) GWT.create(Resources.class);
 * StyleInjector.injectStylesheet(ConstHolder.resources.gwtstyles()
 * .getText(), ConstHolder.resources);
 * 
 * StyleInjector will read the css file and replace occurrences of the
 * method names in this class with their resource equivalent.
 * 
 * 
 * @author Jeff Dwyer
 * 
 */
public interface Resources extends ImmutableResourceBundle {

    /**
     * @gwt.resource gwtstyles.css
     */
    public TextResource gwtstyles();

    /**
     * @gwt.resource hour.png
     */
    public DataResource tl_hour();

    /**
     * @gwt.resource day.png
     */
    public DataResource tl_day();

    /**
     * @gwt.resource week.png
     */
    public DataResource tl_week();

    /**
     * @gwt.resource way3.png
     */
    public DataResource tl_3way();

    /**
     * @gwt.resource month.png
     */
    public DataResource tl_month();

    /**
     * @gwt.resource year.png
     */
    public DataResource tl_year();

    /**
     * @gwt.resource decade.png
     */
    public DataResource tl_decade();

    /**
     * @gwt.resource century3.png
     */
    public DataResource tl_3century();

}
