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
package com.apress.progwt.client.util;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.apress.progwt.client.domain.HasAddress;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LatLngCallback;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.user.client.Timer;

public class BulkGeoCoder extends Timer {

    private List<HasAddress> schools;
    private Geocoder geocoder;
    private String tablename;

    public BulkGeoCoder(List<HasAddress> schools, String tablename) {
        this.schools = schools;
        this.tablename = tablename;
        geocoder = new Geocoder();
    }

    @Override
    public void run() {

        final HasAddress school = schools.remove(0);
        if (school == null) {
            Log.debug("Finished");
            cancel();
            return;
        }
        final String full = school.getFullAddress();
        Geocoder geocoder = new Geocoder();
        geocoder.getLatLng("1600 pennsylvania avenue, washington dc",
                new LatLngCallback() {
                    public void onFailure() {
                    }

                    public void onSuccess(LatLng point) {
                    }
                });

        geocoder.getLatLng(full, new LatLngCallback() {
            public void onFailure() {
                Log
                        .debug("UPDATE "
                                + tablename
                                + " SET latitude = '-1',longitude  = '-1' WHERE id ="
                                + school.getId() + " LIMIT 1 ;");
                run();
            }

            public void onSuccess(LatLng point) {

                Log.debug("UPDATE " + tablename + " SET latitude = '"
                        + point.getLatitude() + "',longitude  = '"
                        + point.getLongitude() + "' WHERE id ="
                        + school.getId() + " LIMIT 1 ;");
                run();
            }
        });
    }

}
