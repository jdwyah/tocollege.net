package com.apress.progwt.client.college.gui.ext;

import com.google.gwt.user.client.Element;

public class JSUtil {
    /**
     * Disable selection for the given element
     * 
     * @param target
     */
    public static native void disableSelect(Element target)/*-{
    if (typeof target.onselectstart!="undefined") //IE route
        target.onselectstart=function(){return false}
    else if (typeof target.style.MozUserSelect!="undefined") //Firefox route
        target.style.MozUserSelect="none"
    else //All other route (ie: Opera)
        target.onmousedown=function(){return false}
    //target.style.cursor = "default"			
    }-*/;

    public static native String getTextSelection()/*-{ 
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
      return txt; 
     } 
     catch( e ){ 
      $wnd.console.log("err:"+e);
      return ""; 
     } 
    }-*/;

}
