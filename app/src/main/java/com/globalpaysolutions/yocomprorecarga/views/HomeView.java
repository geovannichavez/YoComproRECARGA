package com.globalpaysolutions.yocomprorecarga.views;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 19/01/2017.
 */

public interface HomeView
{
    void renderMap();
    void displayActivateLocationDialog();
    void checkPermissions();
    void setInitialUserLocation(Location pLocation);
    void updateUserLocationOnMap(Location pLocation);

    void addSalePoint(String pKey, LatLng pLocation);
    void addSalePointData(String pKey, String pTitle, String pSnippet);
    void removeSalePoint(String pKey);
}
