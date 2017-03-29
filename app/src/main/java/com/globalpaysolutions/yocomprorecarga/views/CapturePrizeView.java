package com.globalpaysolutions.yocomprorecarga.views;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 29/03/2017.
 */

public interface CapturePrizeView
{
    void initialize();
    void updateUserLocation(LatLng pLocation);
    void locationManagerConnected(LatLng pLocation);
}
