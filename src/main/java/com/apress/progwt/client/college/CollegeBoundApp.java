package com.apress.progwt.client.college;

import com.apress.progwt.client.Interactive;
import com.apress.progwt.client.college.gui.MyPage;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.service.remote.GWTSchoolService;
import com.apress.progwt.client.service.remote.GWTSchoolServiceAsync;
import com.apress.progwt.client.service.remote.GWTUserService;
import com.apress.progwt.client.service.remote.GWTUserServiceAsync;
import com.apress.progwt.client.util.Logger;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CollegeBoundApp {
    public static final String MAIN_DIV = "slot1";
    private GWTSchoolServiceAsync schoolService;
    private GWTUserServiceAsync userService;

    private void loadGUI(Widget widget) {
        RootPanel.get("loading").setVisible(false);
        RootPanel.get(MAIN_DIV).add(widget);
    }

    public CollegeBoundApp() {
        try {

            initServices();

            setMeUp();

        } catch (Exception e) {
            error(e);
        }
    }

    private void setMeUp() {
        loadGUI(new MyPage(this, new User()));
    }

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
            Logger.error("Service was null.");
        }

    }

    public GWTSchoolServiceAsync getSchoolService() {
        return schoolService;
    }

    public GWTUserServiceAsync getUserService() {
        return userService;
    }

    protected void error(Exception e) {

        Logger.log("e: " + e);

        e.printStackTrace();

        VerticalPanel panel = new VerticalPanel();

        panel.add(new Label("Error"));
        panel.add(new Label(e.getMessage()));

        RootPanel.get("loading").setVisible(false);
        RootPanel.get("slot1").add(panel);
    }
}
