/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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

    private static final String SECURE_TARGET_URL = "secure/myList.html";

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

        getLoginService().getUserOrDoLogin(SECURE_TARGET_URL,

        new AsyncCallback<User>() {

            public void onFailure(Throwable caught) {
                Log.error("ToCollegeApp.setmeupFailure " + caught);
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
