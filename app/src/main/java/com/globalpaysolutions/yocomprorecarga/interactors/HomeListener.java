package com.globalpaysolutions.yocomprorecarga.interactors;

import com.globalpaysolutions.yocomprorecarga.models.geofire_data.LocationPrizeYCRData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.SalePointData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.VendorPointData;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Josué Chávez on 27/02/2017.
 */

public interface HomeListener
{
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

    //GeoFire GoldPoints
    void gf_goldPoint_onKeyEntered(String pKey, LatLng pLocation);
    void gf_goldPoint_onKeyExited(String pKey);

    //GeoFire SilverPoints
    void gf_silverPoint_onKeyEntered(String pKey, LatLng pLocation);
    void gf_silverPoint_onKeyExited(String pKey);

    //GeoFire BronzePoints
    void gf_bronzePoint_onKeyEntered(String pKey, LatLng pLocation);
    void gf_bronzePoint_onKeyExited(String pKey);

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

    // GeoFire GoldPointsData
    void fb_goldPoint_onDataChange(String pKey, LocationPrizeYCRData pGoldPointData);
    void fb_goldPoint_onCancelled(DatabaseError databaseError);

    // GeoFire SilverPointsData
    void fb_silverPoint_onDataChange(String pKey, LocationPrizeYCRData pSilverPointData);
    void fb_silverPoint_onCancelled(DatabaseError databaseError);

    // GeoFire BronzePointsData
    void fb_bronzePoint_onDataChange(String pKey, LocationPrizeYCRData pBronzePointData);
    void fb_bronzePoint_onCancelled(DatabaseError databaseError);

}
