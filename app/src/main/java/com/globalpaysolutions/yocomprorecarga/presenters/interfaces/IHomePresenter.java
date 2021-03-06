package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 19/01/2017.
 */

public interface IHomePresenter
{
    void setInitialViewsState();
    void chekcLocationServiceEnabled();
    void checkPermissions();
    void connnectToLocationService();
    void onMapReady();
    void disconnectFromLocationService();
    void sendStoreAirtimeReport(String pStoreName, String pAddress, LatLng pLocation, String pFirebaseID);
    void onSalePointClick(String pStoreName, String pAddress, LatLng pLocation, String pFirebaseID);
    void setMapStyle();

    void intializeGeolocation();
    void salesPointsQuery(LatLng pLocation);
    void updateSalePntCriteria(LatLng pLocation);

    void vendorsPointsQuery(LatLng pLocation);
    void updateVendorsPntCriteria(LatLng pLocation);

    void prizePointsQuery(LatLng pLocation);
    void updatePrizePntCriteria(LatLng pLocation); //

    //Other players
    void playersPointsQuery(LatLng location);
    void updatePlayersPntCriteria(LatLng location);

    //Current player
    void writeCurrentPlayerLocation(LatLng location);

    void startShowcase();
    void showcaseMapSeen();

    void setPendingChallenges();
    void navigateToAR();

    void checkWelcomeChest(Location location);
}
