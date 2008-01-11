package com.apress.progwt.client;

import com.apress.progwt.client.calculator.CalculatorApp;
import com.apress.progwt.client.college.ToCollegeApp;
import com.apress.progwt.client.exception.MyUncaughtExceptionHandler;
import com.apress.progwt.client.forum.ForumApp;
import com.apress.progwt.client.map.CollegeMapApp;
import com.apress.progwt.client.util.Logger;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Interactive implements EntryPoint {

    public static final String LOCAL_HOST = "http://localhost:8080/";
    public static final String REMOTE_HOST = "http://www.myhippocampus.com/ProGWT-1.0-SNAPSHOT/";

    /**
     * Switch between localhost for testing &
     */
    public static String getRelativeURL(String url) {
        String realModuleBase;

        if (GWT.isScript()) {

            Logger.log("ModuleBaseURL: " + GWT.getModuleBaseURL());

            String moduleBase = GWT.getModuleBaseURL();

            // Use for Deployment to production server
            //
            realModuleBase = REMOTE_HOST;

            // Use to test compiled browser locally
            //
            if (moduleBase.indexOf("localhost") != -1) {
                Logger.log("Testing. Using Localhost");
                realModuleBase = LOCAL_HOST;
            }

        } else {
            // realModuleBase = GWT.getModuleBaseURL();

            // This is the URL for GWT Hosted mode
            //
            realModuleBase = LOCAL_HOST;
        }

        return realModuleBase + url;
    }

    public static void recordPageHit(String pageName) {
        tickleUrchin(pageName);
    }

    private native static void tickleUrchin(String pageName) /*-{
                                                                                      $wnd.urchinTracker(pageName);
                                                                                  }-*/;

    /**
     * EntryPoint. Dispatch based on javascript dictionary that tells us
     * what we should load.
     * 
     * <script language="JavaScript"> var Vars = { page: "MyMindscape" };
     * </script>
     * 
     */
    public void onModuleLoad() {
        try {
            GWT
                    .setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

            Dictionary dictionary = Dictionary.getDictionary("Vars");

            String widgetCountStr = dictionary.get("widgetCount");
            int widgetCount = Integer.parseInt(widgetCountStr);

            for (int currentWidget = 1; currentWidget <= widgetCount; currentWidget++) {
                String widget = dictionary.get("widget_" + currentWidget);
                if (widget.equals("Calculator")) {
                    CalculatorApp m = new CalculatorApp(currentWidget);
                } else if (widget.equals("CollegeBound")) {
                    Logger.log("Do CollegeBound");
                    ToCollegeApp c = new ToCollegeApp(currentWidget);
                } else if (widget.equals("CollegeMap")) {
                    Logger.log("Do CollegeMap");
                    CollegeMapApp c = new CollegeMapApp(currentWidget);
                } else if (widget.equals("SchoolForum")) {
                    Logger.log("Do School Forum");
                    ForumApp c = new ForumApp(currentWidget);
                } else {
                    throw new Exception("Vars['widget_" + currentWidget
                            + "] => " + widget + " null or no match.");
                }
            }

        } catch (Exception e) {
            Logger.error("e: " + e);

            e.printStackTrace();

            VerticalPanel panel = new VerticalPanel();

            panel.add(new Label("Error"));
            panel.add(new Label(e.getMessage()));

            GWTApp.show(1, panel);

        }

    }
}
