package com.globalpaysolutions.yocomprorecarga.interactors;

import com.firebase.geofire.GeoLocation;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.PendingsResponse;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.PlayerPointData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.SalePointData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.VendorPointData;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Josué Chávez on 27/02/2017.
 */

public interface HomeListener
{
    void onStoreAirtimeReportSuccess(SimpleMessageResponse pResponse);
    void onError(int pCodeStatus, Throwable pThrowable);

    /*
    *
    *
    *   LOCATION POINTS
    *
    */
    // GeoFire StaticPoints
    void gf_salePoint_onKeyEntered(String pKey, LatLng pLocation);
    void gf_salePoint_onKeyExited(String pKey);

    // GeoFire VendorPoints
    void gf_vendorPoint_onKeyEntered(String pKey, LatLng pLocation);
    void gf_vendorPoint_onKeyExited(String pKey);
    void gf_vendorPoint_onKeyMoved(String pKey, LatLng pLocation);
    void gf_vendorPoint_onGeoQueryReady();
    void gf_vendorPoint_onGeoQueryError(DatabaseError pError);

    //GeoFire PlayerPoints
    void gf_playerPoint_onKeyEntered(String key, LatLng location);
    void gf_playerPoint_onKeyExited(String key);
    void gf_playerPoint_onKeyMoved(String key, LatLng location);
    void gf_playerPoint_onGeoQueryReady();
    void gf_playerPoint_onGeoQueryError(DatabaseError pError);


    /*
    *
    *
    *   LOCATION POINTS DATA
    *
    */
    // GeoFire StaticPointsData
    void fb_salePoint_onDataChange(String pKey, SalePointData pSalePointData);
    void fb_salePoint_onCancelled(DatabaseError databaseError);

    // GeoFire VendorPointsData
    void fb_vendorPoint_onDataChange(String pKey, VendorPointData pSalePointData);
    void fb_vendorPoint_onCancelled(DatabaseError databaseError);

    void fb_playerPoint_onDataChange(String key, PlayerPointData playerPointData);
    void fb_playerPoint_onCancelled(DatabaseError databaseError);

    void fb_currentPlayerDataInserted(String key, GeoLocation location);

    void onPendingChallengesSuccess(PendingsResponse body);
    void onPendingChallengesError(int code, Throwable throwable);
}
