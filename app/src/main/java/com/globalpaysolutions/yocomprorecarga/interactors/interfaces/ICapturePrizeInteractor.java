package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.models.api.Tracking;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Target;

/**
 * Created by Josué Chávez on 11/05/2017.
 */

public interface ICapturePrizeInteractor
{
    void retrieveConsumerTracking();
    void openCoinsChest(LatLng pLocation, String pFirebaseID, int pChestType, int pEraID);
    void saveUserTracking(Tracking pTracking);
    void atemptRedeemPrize();
}
