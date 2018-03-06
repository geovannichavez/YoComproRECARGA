package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

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
    void handle2DCoinTouch();
    void handleCoinExchangeKeyUp();
    void touchWildcard_2D(String pFirebaseID, int chestType);
    void showcaseARSeen();
    void checkForWelcomeChest();
    void deleteFirstKeySaved();
    void registerKeyEntered(String pKey, LatLng location, int ageID, String chestType);
}
