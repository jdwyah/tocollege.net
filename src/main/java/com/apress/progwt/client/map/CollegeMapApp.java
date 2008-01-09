package com.apress.progwt.client.map;

import com.apress.progwt.client.GWTApp;
import com.apress.progwt.client.util.Logger;
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
            Logger.log("In CollegeBound");
            double latitude = Double.parseDouble(getParam("latitude"));

            double longitude = Double.parseDouble(getParam("longitude"));

            LatLng collegeCenter = new LatLng(latitude, longitude);

            if (latitude == -1 && longitude == -1) {
                map = new MapWidget();
            } else {
                map = new MapWidget(collegeCenter, DEFAULT_ZOOM);
            }

            map.setSize("500px", "300px");

            map.addControl(new SmallMapControl());
            map.addControl(new MapTypeControl());
            map.setScrollWheelZoomEnabled(true);
            map.clearOverlays();

            map.addOverlay(new Marker(collegeCenter));
            Logger.log("Show CollegeBound");

            show(map);

        } catch (Exception e) {
            Logger.log("EX " + e);
            loadError(e);
        }

    }

}
