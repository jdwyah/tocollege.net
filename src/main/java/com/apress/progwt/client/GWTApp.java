package com.apress.progwt.client;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.college.LoginService;
import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.college.gui.status.StatusPanel;
import com.apress.progwt.client.rpc.StdAsyncCallback;
import com.apress.progwt.client.service.remote.GWTSchoolService;
import com.apress.progwt.client.service.remote.GWTSchoolServiceAsync;
import com.apress.progwt.client.service.remote.GWTUserService;
import com.apress.progwt.client.service.remote.GWTUserServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GWTApp {

    private int pageID;

    private GWTSchoolServiceAsync schoolService;
    private GWTUserServiceAsync userService;

    private ServiceCache serviceCache;
    private LoginService loginService;

    public GWTApp(int pageID) {
        this.pageID = pageID;

        // setup the StatusPanel. There will be just one DIV for this, no
        // matter how many apps we have in the same page.
        try {
            RootPanel status = RootPanel.get("gwt-status");
            if (status.getWidgetCount() == 0) {
                status.add(new StatusPanel());
            }
            StdAsyncCallback
                    .setManager((StatusPanel) status.getWidget(0));
        } catch (Exception e) {
            Log.error("Status Panel problem: ");
        }
    }

    protected String getLoadID() {
        return getLoadID(pageID);
    }

    protected String getPreLoadID() {
        return getPreLoadID(pageID);
    }

    private static String getLoadID(int id) {
        return "gwt-slot-" + id;
    }

    protected String getParam(String string) {
        Dictionary dictionary = Dictionary.getDictionary("Vars");
        return dictionary.get(string + "_" + pageID);
    }

    private static String getPreLoadID(int id) {
        return "gwt-loading-" + id;
    }

    protected void loadError(Exception e) {

        Log.error("e: " + e);

        e.printStackTrace();

        VerticalPanel panel = new VerticalPanel();

        panel.add(new Label("Error"));
        panel.add(new Label(e.getMessage()));

        RootPanel.get(getPreLoadID()).setVisible(false);
        RootPanel.get(getLoadID()).add(panel);
    }

    protected void show(Widget panel) {
        show(pageID, panel);
    }

    public static void show(int id, Widget panel) {
        RootPanel.get(getPreLoadID(id)).setVisible(false);
        RootPanel.get(getLoadID(id)).add(panel);
    }

    public LoginService getLoginService() {
        return loginService;
    }

    /**
     * call initServices if your GWTApp would like the asynchronous
     * services to be setup
     */
    protected void initServices() {

        schoolService = (GWTSchoolServiceAsync) GWT
                .create(GWTSchoolService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) schoolService;

        String pre = Interactive.getRelativeURL("service/");

        endpoint.setServiceEntryPoint(pre + "schoolService");

        userService = (GWTUserServiceAsync) GWT
                .create(GWTUserService.class);
        ServiceDefTarget endpointUser = (ServiceDefTarget) userService;
        endpointUser.setServiceEntryPoint(pre + "userService");

        if (schoolService == null || userService == null) {
            Log.error("Service was null.");
        }

        serviceCache = new ServiceCache(this);
        loginService = new LoginService(serviceCache);
    }

    public ServiceCache getServiceCache() {
        return serviceCache;
    }

    public GWTSchoolServiceAsync getSchoolService() {
        return schoolService;
    }

    public GWTUserServiceAsync getUserService() {
        return userService;
    }
}
