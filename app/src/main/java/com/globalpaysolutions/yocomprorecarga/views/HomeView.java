package com.globalpaysolutions.yocomprorecarga.views;

import android.location.Location;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
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
    void showCustomStoreReportDialog(DialogViewModel pStoreReport, String pStoreName, String pAddress, LatLng pLocation, String pFirebaseID);
    void showSuccessMessage(DialogViewModel pMessageModel);
    void showErrorMessage(DialogViewModel pMessageModel);
    void showLoadingDialog(String pLabel);
    void hideLoadingDialog();
    void swtichMapStyle(boolean isNightTime);

    void addSalePoint(String pKey, LatLng pLocation);
    void addSalePointData(String pKey, String pTitle, String pSnippet);
    void removeSalePoint(String pKey);

    void addVendorPoint(String pKey, LatLng pLocation);
    void addVendorPointData(String pKey, String pTitle, String pSnippet);
    void moveVendorPoint(String pKey, LatLng pLocation);
    void removeVendorPoint(String pKey);

    void addGoldPoint(String pKey, LatLng pLocation);
    void addGoldPointData(String pKey, String pTitle, String pSnippet);
    void removeGoldPoint(String pKey);

    void addSilverPoint(String pKey, LatLng pLocation);
    void addSilverPointData(String pKey, String pTitle, String pSnippet);
    void removeSilverPoint(String pKey);

    void addBronzePoint(String pKey, LatLng pLocation);
    void addBronzePointData(String pKey, String pTitle, String pSnippet);
    void removeBronzePoint(String pKey);
}
