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
}
