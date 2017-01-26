package com.globalpaysolutions.yocomprorecarga.views;

import android.location.Location;

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
}
