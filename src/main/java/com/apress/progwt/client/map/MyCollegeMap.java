package com.apress.progwt.client.map;

import com.apress.progwt.client.college.ServiceCache;
import com.apress.progwt.client.college.gui.MyPageTab;
import com.apress.progwt.client.college.gui.SchoolLink;
import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.domain.School;
import com.apress.progwt.client.domain.User;
import com.apress.progwt.client.util.Logger;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.MapTypeControl;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.event.MarkerClickListener;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

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

    private DialogBox dialogBox;

    private MapWidget map;

    private User user;

    private Button showB;

    public MyCollegeMap(ServiceCache serviceCache) {

        dialogBox = new DialogBox(true);
        dialogBox.setText("College Map");

        dialogBox.setPixelSize(500, 300);
        map = new MapWidget(middleAmerica, 4);
        map.setSize("500px", "300px");

        map.addControl(new SmallMapControl());
        map.addControl(new MapTypeControl());
        map.setScrollWheelZoomEnabled(true);
        dialogBox.add(map);

        showB = new Button("Show");
        showB.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                dialogBox.show();
            }
        });

        initWidget(showB);
        dialogBox.setPopupPosition(100, 100);
    }

    private Marker createMarker(final School school) {
        LatLng point = new LatLng(school.getLatitude(), school
                .getLongitude());
        if (point.getLatitude() == -1 && point.getLongitude() == -1) {
            return null;
        }

        final Marker marker = new Marker(point);
        marker.addClickListener(new MarkerClickListener() {
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
        Logger.log("Refreshing MyCollegeMap " + getAbsoluteLeft() + " "
                + getAbsoluteTop() + " " + showB.getAbsoluteLeft() + " ");

        dialogBox.setPopupPosition(100, 100);

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

        dialogBox.show();

    }
}
