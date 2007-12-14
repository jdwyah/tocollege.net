package com.apress.progwt.client.college;

import com.apress.progwt.client.GWTApp;
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

public class ToCollegeApp extends GWTApp {

    private GWTSchoolServiceAsync schoolService;
    private GWTUserServiceAsync userService;

    private ServiceCache serviceCache;
    private LoginService loginService;

    private void loadGUI(Widget widget) {
        RootPanel.get(getPreLoadID()).setVisible(false);
        RootPanel.get(getLoadID()).add(widget);
    }

    public ToCollegeApp(int pageID) {
        super(pageID);
        try {

            initConstants();

            initServices();

            setMeUp();

        } catch (Exception e) {
            error(e);
        }
    }

    private void setMeUp() {
        final MyPage myPage = new MyPage(this);
        loadGUI(myPage);
        loginService.getUserOrDoLogin(new AsyncCallback<User>() {

            public void onFailure(Throwable caught) {
                System.out.println("setmeupFailure " + caught);
            }

            public void onSuccess(User result) {
                myPage.load(result);
            }
        });

        // schoolService.getAllSchools(new StdAsyncCallback<List<School>>(
        // "GetAllSchools") {
        //
        // public void onSuccess(List<School> result) {
        // List<HasAddress> schoolAddrs = new ArrayList<HasAddress>();
        // schoolAddrs.addAll(result);
        //
        // BulkGeoCoder geoCodeTimer = new BulkGeoCoder(schoolAddrs,
        // "school");
        // geoCodeTimer.schedule(4000);
        // }
        //
        // });
    }

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

        RootPanel.get(getPreLoadID()).setVisible(false);
        RootPanel.get(getLoadID()).add(panel);
    }

    public LoginService getLoginService() {
        return loginService;
    }
}
