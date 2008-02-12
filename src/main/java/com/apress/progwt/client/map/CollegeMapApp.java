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
package com.apress.progwt.client.map;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.GWTApp;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.MapTypeControl;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;

public class CollegeMapApp extends GWTApp {

    private static final int DEFAULT_ZOOM = 6;

    private MapWidget map;

    public CollegeMapApp(int pageID) {
        super(pageID);

        try {
            Log.debug("In CollegeBound");
            double latitude = Double.parseDouble(getParam("latitude"));

            double longitude = Double.parseDouble(getParam("longitude"));

            LatLng collegeCenter = new LatLng(latitude, longitude);

            if (latitude == -1 && longitude == -1) {
                map = new MapWidget();
            } else {
                map = new MapWidget(collegeCenter, DEFAULT_ZOOM);
            }

            map.setSize("300px", "250px");

            map.addControl(new SmallMapControl());
            map.addControl(new MapTypeControl());
            map.setScrollWheelZoomEnabled(true);
            map.clearOverlays();

            map.addOverlay(new Marker(collegeCenter));
            Log.debug("Show CollegeBound");

            show(map);

        } catch (Exception e) {
            Log.error("EX " + e);
            loadError(e);
        }

    }

}
