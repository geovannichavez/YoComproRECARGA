package com.globalpaysolutions.yocomprorecarga.views;

import android.graphics.Bitmap;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.LocationPrizeYCRData;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Josué Chávez on 29/03/2017.
 */

public interface CapturePrizeView
{
    void updateUserLocation(double pLatitude, double pLongitude, double pAccuracy);
    void locationManagerConnected(double pLatitude, double pLongitude, double pAccuracy);
    void onPOIClick();
    void onCoinLongClick();
    void hideArchViewLoadingMessage();
    void showGenericDialog(DialogViewModel pMessageModel);
    void showImageDialog(DialogViewModel dialogModel, int resource);
    void showSouvenirWonDialog(String souvenirName, String souvenirDescription, String url);
    void showPrizeColectedDialog(DialogViewModel pDialogModel);
    void showLoadingDialog(String pLabel);
    void hideLoadingDialog();
    void obtainUserProgress();
    void switchRecarcoinVisible(boolean pVisible);
    void blinkRecarcoin();
    void stopVibrate();
    void showToast(String pText);
    void removeBlinkingAnimation();
    void onChestTouch(int pAwait);
    void removeRunnableCallback();
    void deleteModelAR();



    void updateIndicators(String pPrizes, String pCoins, String pSouvenirs);
    void updatePrizeButton(int pCoins);

    void onGoldKeyEntered(String pKey, LatLng pLocation);
    void onGoldKeyExited(String pKey);
    void onGoldKeyEntered_2D(String pKey, LatLng pLocation);
    void onGoldPointDataChange(String pKey, LocationPrizeYCRData pGoldPointData);
    void onGoldPointCancelled(DatabaseError pDatabaseError);

    void onSilverKeyEntered(String pKey, LatLng pLocation);
    void onSilverKeyEntered_2D(String pKey, LatLng pLocation);
    void onSilverKeyExited(String pKey);
    void onSilverPointDataChange(String pKey, LocationPrizeYCRData pGoldPointData);
    void onSilverPointCancelled(DatabaseError pDatabaseError);

    void onBronzeKeyEntered(String pKey, LatLng pLocation);
    void onBronzeKeyEntered_2D(String pKey, LatLng pLocation);
    void onBronzeKeyExited(String pKey);
    void onBronzePointDataChange(String pKey, LocationPrizeYCRData pGoldPointData);
    void onBronzePointCancelled(DatabaseError pDatabaseError);
}
