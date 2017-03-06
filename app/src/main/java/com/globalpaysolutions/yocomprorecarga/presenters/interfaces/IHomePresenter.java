package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import android.location.Location;

/**
 * Created by Josué Chávez on 19/01/2017.
 */

public interface IHomePresenter
{
    void setInitialViewsState();
    void checkUserDataComplited();
    void chekcLocationServiceEnabled();
    void checkPermissions();
    void connnectToLocationService();
    void onMapReady();
    void disconnectFromLocationService();


}
