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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Grid;

/**
 * Extension of Andrea Maldini's MyGrid
 * http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/94eb5c9285d73b00/4fb2b3fa62e5e134
 * 
 * @author Jeff Dwyer
 * 
 */
public class TableWithHeaders extends Grid {

    /**
     * 
     * @param rows
     * 
     * @param columns -
     *            Variable number of header columns
     * 
     */
    public TableWithHeaders(int rows, String... columns) {

        super(rows, columns.length);

        Log.debug("TableWithHeaders.NEW GRID " + columns.length + " "
                + rows);

        // use DOM to create thead element....
        Element thead = DOM.createElement("thead");
        Element tr = DOM.createTR();

        // add columns
        DOM.appendChild(thead, tr);
        for (String columnName : columns) {
            Element th = DOM.createTH();
            DOM.appendChild(tr, th);

            // add some text to the header...
            DOM.setInnerText(th, columnName);
        }

        // get the table element
        Element table = this.getElement();

        // and add the thead before the tbody
        DOM.insertChild(table, thead, 0);
    }

}
