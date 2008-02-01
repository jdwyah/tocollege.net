package com.apress.progwt.client.college;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.GWTApp;
import com.apress.progwt.client.college.gui.MyPage;
import com.apress.progwt.client.domain.User;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ToCollegeApp extends GWTApp {

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
        long start = System.currentTimeMillis();
        final MyPage myPage = new MyPage(this);
        loadGUI(myPage);
        long end = System.currentTimeMillis();
        Log.debug("GUI: " + (end - start) / 1000.0);

        final long start2 = System.currentTimeMillis();

        getLoginService().getUserOrDoLogin(new AsyncCallback<User>() {

            public void onFailure(Throwable caught) {
                Log.error("setmeupFailure " + caught);
            }

            public void onSuccess(User result) {
                long end2 = System.currentTimeMillis();
                Log.debug("async: " + (end2 - start2) / 1000.0);
                long start3 = System.currentTimeMillis();
                myPage.load(result);
                long end3 = System.currentTimeMillis();
                Log.debug("load: " + (end3 - start3) / 1000.0);

            }
        });

        // getSchoolService().getAllSchools(
        // new StdAsyncCallback<List<School>>("GetAllSchools") {
        //
        // public void onSuccess(List<School> result) {
        // List<HasAddress> schoolAddrs = new ArrayList<HasAddress>();
        // schoolAddrs.addAll(result);
        //
        // BulkGeoCoder geoCodeTimer = new BulkGeoCoder(
        // schoolAddrs, "school");
        // geoCodeTimer.schedule(4000);
        // }
        //
        // });
    }

    protected void error(Exception e) {

        Log.error("e: " + e);

        e.printStackTrace();

        VerticalPanel panel = new VerticalPanel();

        panel.add(new Label("Error"));
        panel.add(new Label(e.getMessage()));

        RootPanel.get(getPreLoadID()).setVisible(false);
        RootPanel.get(getLoadID()).add(panel);
    }

}
