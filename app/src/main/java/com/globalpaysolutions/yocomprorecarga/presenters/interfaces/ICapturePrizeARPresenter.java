package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import com.globalpaysolutions.yocomprorecarga.utils.CustomClickListener;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 29/03/2017.
 */

public interface ICapturePrizeARPresenter
{
    void initialize();
    void resume();
    void prizePointsQuery(LatLng pLocation);
    void updatePrizePntCriteria(LatLng pLocation);
    void exchangeCoinsChest(String pArchitectURL);
    void exchangeCoinsChest_2D(LatLng pLocation, String pFirebaseID, int pChestType);
    void retrieveUserTracking();
    void redeemPrize();
    void handleCoinTouch();
    void handleCoinExchangeKeyUp();
    void exchangeWildcard_2D(LatLng pLocation, String pFirebaseID, int pChestType);
}
