package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 31/03/2017.
 */

public interface IFirebasePOIInteractor
{
    void initializePOIGeolocation();
    void goldPointsQuery(GeoLocation pLocation, double pRadius);
    void goldPointsUpdateCriteria(GeoLocation pLocation, double pRadius);
    void silverPointsQuery(GeoLocation pLocation, double pRadius);
    void silverPointsUpdateCriteria(GeoLocation pLocation, double pRadius);
    void bronzePointsQuery(GeoLocation pLocation, double pRadius);
    void bronzePointsUpdateCriteria(GeoLocation pLocation, double pRadius);
    void wildcardPointsQuery(GeoLocation location, double radius);
    void wildcardPointsUpdateCriteria(GeoLocation pLocation, double pRadius);
    void sponsorPrizeQuery(GeoLocation location, double radius);
    void sponsorPrizeQueryUpdateCriteria(GeoLocation location, double radius);
    void retrieveSponsorPrizeData(String key, LatLng location);
    void detachFirebaseListeners();


}
