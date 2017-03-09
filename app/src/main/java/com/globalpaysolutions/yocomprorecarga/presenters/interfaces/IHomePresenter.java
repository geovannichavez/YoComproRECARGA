package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import android.location.Location;

import com.globalpaysolutions.yocomprorecarga.interactors.HomeListener;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 19/01/2017.
 */

public interface IHomePresenter
{
    void setInitialViewsState();
    void checkUserDataCompleted();
    void chekcLocationServiceEnabled();
    void checkPermissions();
    void connnectToLocationService();
    void onMapReady();
    void disconnectFromLocationService();

    void intializeGeolocation();
    void salesPointsQuery(LatLng pLocation);
    void updateSalePntCriteria(LatLng pLocation);

    void vendorPointsQuery(LatLng pLocation);
    void updateVendorePntCriteria(LatLng pLocation);
}
