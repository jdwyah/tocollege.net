package com.apress.progwt.client;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.calculator.CalculatorApp;
import com.apress.progwt.client.college.ToCollegeApp;
import com.apress.progwt.client.forum.ForumApp;
import com.apress.progwt.client.map.CollegeMapApp;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
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

            Log.info("ModuleBaseURL: " + GWT.getModuleBaseURL());

            String moduleBase = GWT.getModuleBaseURL();

            // Use for Deployment to production server
            //
            realModuleBase = REMOTE_HOST;

            // Use to test compiled browser locally
            //
            if (moduleBase.indexOf("localhost") != -1) {
                Log.info("Testing. Using Localhost");
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
     * Initial entry point. Make sure our exception handler is set before
     * we begin.
     */
    public void onModuleLoad() {

        // could use Log.setUncaughtExceptionHandler(), but we'd like the
        // stacktrace too.
        GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable arg0) {
                arg0.printStackTrace();
                Log.error(arg0.getMessage());
            }
        });

        DeferredCommand.addCommand(new Command() {
            public void execute() {
                onModuleLoad2();
            }
        });
    }

    /**
     * Real EntryPoint. Dispatch based on javascript dictionary that tells
     * us what we should load.
     * 
     * <script language="JavaScript"> var Vars = { page: "MyMindscape" };
     * </script>
     * 
     */
    public void onModuleLoad2() {
        try {

            Dictionary dictionary = Dictionary.getDictionary("Vars");

            String widgetCountStr = dictionary.get("widgetCount");
            int widgetCount = Integer.parseInt(widgetCountStr);

            for (int currentWidget = 1; currentWidget <= widgetCount; currentWidget++) {
                String widget = dictionary.get("widget_" + currentWidget);
                Log.info("Do " + widget);
                if (widget.equals("Calculator")) {
                    CalculatorApp m = new CalculatorApp(currentWidget);
                } else if (widget.equals("CollegeBound")) {
                    ToCollegeApp c = new ToCollegeApp(currentWidget);
                } else if (widget.equals("CollegeMap")) {
                    CollegeMapApp c = new CollegeMapApp(currentWidget);
                } else if (widget.equals("Forum")) {
                    ForumApp c = new ForumApp(currentWidget);
                } else if (widget.equals("VerticalLabel")) {
                    VerticalLabelApp c = new VerticalLabelApp(
                            currentWidget);
                } else {
                    throw new Exception("Vars['widget_" + currentWidget
                            + "] => " + widget + " null or no match.");
                }
            }

        } catch (Exception e) {
            Log.error("e: " + e);

            e.printStackTrace();

            VerticalPanel panel = new VerticalPanel();

            panel.add(new Label("Error"));
            panel.add(new Label(e.getMessage()));

            GWTApp.show(1, panel);

        }

    }
}
