package com.globalpaysolutions.yocomprorecarga.interactors;

import com.firebase.geofire.GeoLocation;
import com.globalpaysolutions.yocomprorecarga.models.data.SalePointData;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Josué Chávez on 27/02/2017.
 */

public interface HomeListener
{
    void gf_salePoint_onKeyEntered(String pKey, LatLng pLocation);
    void gf_salePoint_onKeyExited(String pKey);
    //void gf_salePoint_onKeyMoved(String pKey, LatLng pLocation);
    //void gf_salePoint_onGeoQueryReady();
    //void gf_salePoint_onGeoQueryError(DatabaseError pError);

    void gf_salePoint_onDataChange(String pKey, SalePointData pSalePointData);
    void gf_salePoint_onCancelled(DatabaseError databaseError);

}
