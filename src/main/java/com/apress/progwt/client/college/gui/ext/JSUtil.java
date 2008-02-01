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
              return txt; 
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
    $wnd.urchinTracker(pageName);
    }-*/;

    /**
     * return <head> element
     * 
     * @return
     */
    public static native Element getDocumentHead()
    /*-{
    return $doc.getElementsByTagName("head")[0];
    }-*/;

    /**
     * DOM.setInnerText() on a <script> element fails bc IE <script> is
     * "noscope" See
     * http://forums.microsoft.com/MSDN/ShowPost.aspx?PostID=909362&SiteID=1
     * for details.
     * 
     * Allow us to get around this 'security' feature by
     * 
     * TODO replace with DeferredBinding garr! this wasn't needed. They
     * already did it. <inherits
     * name="com.google.gwt.libideas.StyleInjector" />
     * 
     * @param elem -
     *            Must be a <script> element
     * @param rules
     * @return
     */
    public static native String setScriptRules(Element elem, String rules)
    /*-{
    var names = "";
    try {    
        elem.innerText = text || '';    
    }
    catch(exc) {            
        var parts = rules.split(/[{}]/);        
        for (var i=0; i<parts.length -1; i+=2){
            try{
                names += "N:"+parts[i]+"||\n";
                names += "B:"+parts[i+1]+"<<\n";
                elem.styleSheet.addRule(parts[i],parts[i+1]);
            }catch(err){                
                alert("setScriptRules error "+parts[i]+" "+parts[i+1])
                return names;
            }            
        }
    }
    return names;
    }-*/;
}
