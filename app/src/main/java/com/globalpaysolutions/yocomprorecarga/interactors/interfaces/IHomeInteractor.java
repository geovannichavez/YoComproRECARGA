package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.globalpaysolutions.yocomprorecarga.interactors.HomeListener;

/**
 * Created by Josué Chávez on 24/02/2017.
 */

public interface IHomeInteractor
{
    void intializeGeolocation();
    void salesPointsQuery(GeoLocation pLocation);
    void salesPointsUpdateCriteria(GeoLocation pLocation);
    void vendorPointsQuery(GeoLocation pLocation);
    void vendorPointsUpdateCriteria(GeoLocation pLocation);

    void goldPointsQuery(GeoLocation pLocation);
    void goldPointsUpdateCriteria(GeoLocation pLocation);
    void silverPointsQuery(GeoLocation pLocation);
    void silverPointsUpdateCriteria(GeoLocation pLocation);
    void bronzePointsQuery(GeoLocation pLocation);
    void bronzePointsUpdateCriteria(GeoLocation pLocation);
}
