package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.firebase.geofire.GeoLocation;

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
    void detachFirebaseListeners();
}
