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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ToCollegeApp {
    public static final String MAIN_DIV = "slot1";
    private GWTSchoolServiceAsync schoolService;
    private GWTUserServiceAsync userService;

    private ServiceCache serviceCache;
    private LoginService loginService;

    private void loadGUI(Widget widget) {
        RootPanel.get("loading").setVisible(false);
        RootPanel.get(MAIN_DIV).add(widget);
    }

    public ToCollegeApp() {
        try {

            initConstants();

            initServices();

            setMeUp();

        } catch (Exception e) {
            error(e);
        }
    }

    private void setMeUp() {

        loginService.getUserOrDoLogin(new AsyncCallback<User>() {

            public void onFailure(Throwable caught) {
                System.out.println("setmeupFailure " + caught);
            }

            public void onSuccess(User result) {
                loadGUI(new MyPage(ToCollegeApp.this, result));
            }
        });

        // schoolService.getAllSchools(new AsyncCallback<List<School>>() {
        //
        // public void onFailure(Throwable caught) {
        // // TODO Auto-generated method stub
        //
        // }
        //
        // public void onSuccess(List<School> result) {
        //
        // GeoTimer geoCodeTimer = new GeoTimer(result);
        // geoCodeTimer.scheduleRepeating(10000);
        // }
        // });
    }

    // private class GeoTimer extends Timer {
    //
    // private List<School> schools;
    // private Geocoder geocoder;
    //
    // public GeoTimer(List<School> schools) {
    // this.schools = schools;
    // geocoder = new Geocoder();
    //
    // }
    //
    // @Override
    // public void run() {
    // School school = schools.remove(0);
    // if (school == null) {
    // System.out.println("Finished");
    // cancel();
    // }
    // final String full = school.getFullAddress();
    // System.out.println("Fetch " + full);
    // geocoder.getLatLng(full, new LatLngCallback() {
    // public void onFailure() {
    // System.out.println("Failure: " + full);
    // }
    //
    // public void onSuccess(LatLng point) {
    // System.out.println("succ : " + point);
    // }
    // });
    // }
    //
    // }

    private void initConstants() {
        // ConstHolder.myConstants = (Consts) GWT.create(Consts.class);
        // ConstHolder.images = (Images) GWT.create(Images.class);
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

    protected void error(Exception e) {

        Logger.log("e: " + e);

        e.printStackTrace();

        VerticalPanel panel = new VerticalPanel();

        panel.add(new Label("Error"));
        panel.add(new Label(e.getMessage()));

        RootPanel.get("loading").setVisible(false);
        RootPanel.get("slot1").add(panel);
    }

    public LoginService getLoginService() {
        return loginService;
    }
}
