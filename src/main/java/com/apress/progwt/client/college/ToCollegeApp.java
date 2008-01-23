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
        final MyPage myPage = new MyPage(this);
        loadGUI(myPage);
        getLoginService().getUserOrDoLogin(new AsyncCallback<User>() {

            public void onFailure(Throwable caught) {
                System.out.println("setmeupFailure " + caught);
            }

            public void onSuccess(User result) {
                myPage.load(result);
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

    private void initConstants() {
        // ConstHolder.myConstants = (Consts) GWT.create(Consts.class);
        // ConstHolder.images = (Images) GWT.create(Images.class);
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
