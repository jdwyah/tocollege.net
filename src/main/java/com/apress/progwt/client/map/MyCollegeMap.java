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

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.college.gui.MyPageTab;
import com.apress.progwt.client.college.gui.SchoolLink;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.MapTypeControl;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.event.MarkerClickListener;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Unfortunately, GoogleMaps don't play well with Tabs, so we have to do
 * some trickery here. This isn't purely a GWT specific issue, but
 * something that stems from trying to use heavyweight javascript widgets
 * like the maps when we're creating the UI dynamically. See
 * http://www.dynamicdrive.com/forums/archive/index.php/t-3923.html
 * 
 * 
 * 
 * @author Jeff Dwyer
 * 
 */
public class MyCollegeMap extends Composite implements MyPageTab {

    private static final LatLng middleAmerica = new LatLng(37.0625,
            -95.677068);

    private MapWidget map;

    private User user;

    public MyCollegeMap(ServiceCache serviceCache) {

        map = new MapWidget(middleAmerica, 4);        
        map.setPixelSize(760, 300);

        map.addControl(new SmallMapControl());
        map.addControl(new MapTypeControl());
        map.setScrollWheelZoomEnabled(true);

        SimplePanel sizeCorrector = new SimplePanel();
        sizeCorrector.add(map);
        sizeCorrector.setPixelSize(760,300);
        
        initWidget(sizeCorrector);
        
        
    }

    private Marker createMarker(final School school) {
        LatLng point = new LatLng(school.getLatitude(), school
                .getLongitude());
        if (point.getLatitude() == -1 && point.getLongitude() == -1) {
            return null;
        }

        final Marker marker = new Marker(point);
        marker.addMarkerClickListener(new MarkerClickListener() {
            public void onClick(Marker sender) {
                InfoWindow info = map.getInfoWindow();
                info.open(sender, new InfoWindowContent(new SchoolLink(
                        school)));
            }

            public void onDoubleClick(Marker sender) {
            }
        });
        return marker;
    }

    public String getHistoryName() {
        return "MyCollegeMap";
    }

    public void load(User user) {
        this.user = user;
    }

    public void refresh() {
        map.checkResize();

        if (user != null) {
            load(user);

            map.clearOverlays();
            for (Application app : user.getSchoolRankings()) {

                Marker marker = createMarker(app.getSchool());
                if (marker != null) {
                    map.addOverlay(marker);
                }
            }
        }


    }
}
