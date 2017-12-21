package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import android.graphics.Bitmap;

import com.firebase.geofire.GeoLocation;

/**
 * Created by Josué Chávez on 24/02/2017.
 */

public interface IHomeInteractor
{
    void initializeGeolocation();
    void salesPointsQuery(GeoLocation pLocation);
    void salesPointsUpdateCriteria(GeoLocation pLocation);
    void vendorPointsQuery(GeoLocation pLocation);
    void vendorPointsUpdateCriteria(GeoLocation pLocation);
    void sendStoreAirtimeReport(String pStoreName, String pAddressStore, double pLongitude, double pLatitude, String pFirebaseID);
    Bitmap fetchBitmap(String url);
    void fetchGoldMarker(String url);
    void fetchSilverMarker(String url);
    void fetchBronzeMarker(String url);
    void fetchWildcardMarker(String url);
}
