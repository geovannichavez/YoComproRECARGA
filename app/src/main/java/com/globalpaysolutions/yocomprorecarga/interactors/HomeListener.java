package com.globalpaysolutions.yocomprorecarga.interactors;

import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Josué Chávez on 27/02/2017.
 */

public interface HomeListener
{
    void geoFireOnKeyEntered(String pKey, LatLng pLocation);
    void geoFireOnKeyExited(String pKey);
    void geoFireOnKeyMoved(String pKey, LatLng pLocation);
    void geoFireoOnGeoQueryReady();
    void geoFireOnGeoQueryError(DatabaseError pError);

    void onSuccess();
    void onError();
}
