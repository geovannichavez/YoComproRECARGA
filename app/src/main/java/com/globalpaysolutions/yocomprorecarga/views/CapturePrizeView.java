package com.globalpaysolutions.yocomprorecarga.views;

/**
 * Created by Josué Chávez on 29/03/2017.
 */

public interface CapturePrizeView
{
    void updateUserLocation(double pLatitude, double pLongitude, double pAccuracy);
    void locationManagerConnected(double pLatitude, double pLongitude, double pAccuracy);
}
