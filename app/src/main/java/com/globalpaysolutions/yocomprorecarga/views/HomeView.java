package com.globalpaysolutions.yocomprorecarga.views;

import android.graphics.Bitmap;
import android.location.Location;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.MarkerData;
import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

/**
 * Created by Josué Chávez on 19/01/2017.
 */

public interface HomeView
{
    void renderMap();
    void setClickListeners();
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
    void showInfographyDialog();
    void getMarkerBitmaps(Map<String, Bitmap> markerMap);

    void addSalePoint(String pKey, LatLng pLocation);
    void addSalePointData(String pKey, String pTitle, String pSnippet, MarkerData markerData);
    void removeSalePoint(String pKey);

    void addVendorPoint(String pKey, LatLng pLocation);
    void addVendorPointData(String pKey, String pTitle, MarkerData pMarkerData);
    void moveVendorPoint(String pKey, LatLng pLocation);
    void removeVendorPoint(String pKey);

    void addGoldPoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp);
    void addGoldPointData(String pKey, String pTitle, String pSnippet);
    void removeGoldPoint(String pKey);

    void addSilverPoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp);
    void addSilverPointData(String pKey, String pTitle, String pSnippet);
    void removeSilverPoint(String pKey);

    void addBronzePoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp);
    void addBronzePointData(String pKey, String pTitle, String pSnippet);
    void removeBronzePoint(String pKey);

    void addWildcardPoint(String pKey, LatLng pLocation, Bitmap pMarkerBmp);
    void addWildcardPointData(String pKey, String brand, String title, String message);
    void removeWildcardPoint(String pKey);

    void addPlayerPoint(String key, LatLng location);
    void addPlayerPointData(String key, String title, String snippet, MarkerData markerData);
    void movePlayerPoint(String key, LatLng location);
    void removePlayerPoint(String key);

    void showToast(String string);
    void startShowcase();


}
