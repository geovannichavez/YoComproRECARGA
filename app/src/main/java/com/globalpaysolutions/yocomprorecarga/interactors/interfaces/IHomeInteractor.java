package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.firebase.geofire.GeoLocation;
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
    void playersPointsQuery(GeoLocation location);
    void playersPointsUpdateCriteria(GeoLocation location);
    void insertCurrentPlayerData(GeoLocation location, String facebookID);
    void setPlayerLocation(String key, GeoLocation location);
    void deletePlayerLocation(String key);

    void sendStoreAirtimeReport(String pStoreName, String pAddressStore, double pLongitude, double pLatitude, String pFirebaseID);
    void getPendingChallenges(HomeListener listener);

    //void downloadMarkerBmp(String markerUrl, String markerName, HomeListener listener);
}
