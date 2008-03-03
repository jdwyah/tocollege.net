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

import com.google.gwt.user.client.Element;

public class JSUtil {
    /**
     * Disable selection for the given element
     * 
     * @param target
     */
    public static native void disableSelect(Element target)
    /*-{
            if (typeof target.onselectstart!="undefined") //IE route
                target.onselectstart=function(){return false}
            else if (typeof target.style.MozUserSelect!="undefined") //Firefox route
                target.style.MozUserSelect="none"
            else //All other route (ie: Opera)
                target.onmousedown=function(){return false}
            //target.style.cursor = "default"			
            }-*/;

    /**
     * note the important return txt+"" Without this, the 'String' object
     * that is returned is a weirdo. Calling length() on it returns
     * undefined on Firefox.
     * 
     * @return
     */
    public static native String getTextSelection()
    /*-{ 
            try{              
             if ($wnd.getSelection) 
              { 
                txt = $wnd.getSelection(); 
              } 
              else if ($doc.getSelection) 
              { 
                txt = $doc.getSelection(); 
              } 
              else if ($doc.selection) 
               { 
                txt = $doc.selection.createRange().text; 
               }           
              return txt+""; 
             } 
             catch( e ){ 
              $wnd.console.log("err:"+e);
              return ""; 
             } 
            }-*/;

    /**
     * perform JavaScript escape() function
     * 
     * @param input
     * @return
     */
    public static native String escape(String input)
    /*-{
            return escape(input);    
            }-*/;

    /**
     * tickle the GoogleAnalytics urhin to record a page hit.
     * 
     * @param pageName
     */
    public static native void tickleUrchin(String pageName)
    /*-{
    $wnd.pageTracker._trackPageview(pageName);
    }-*/;

}
