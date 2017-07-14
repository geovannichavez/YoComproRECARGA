package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.models.api.Tracking;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 11/05/2017.
 */

public interface ICapturePrizeInteractor
{
    void retrieveConsumerTracking();
    void exchangePrizeData(LatLng pLocation, String pFirebaseID, int pChestType);
    void saveUserTracking(Tracking pTracking);
}
