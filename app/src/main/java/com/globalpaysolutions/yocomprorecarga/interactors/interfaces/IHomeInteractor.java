package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.globalpaysolutions.yocomprorecarga.interactors.HomeListener;

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
}
