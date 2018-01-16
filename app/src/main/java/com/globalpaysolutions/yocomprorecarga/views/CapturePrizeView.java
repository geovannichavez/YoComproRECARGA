package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.LocationPrizeYCRData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.WildcardYCRData;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Josué Chávez on 29/03/2017.
 */

public interface CapturePrizeView
{
    void updateUserLocation(double pLatitude, double pLongitude, double pAccuracy);
    void locationManagerConnected(double pLatitude, double pLongitude, double pAccuracy);

    void on3DChestClick();
    void onCoinLongClick();
    void hideArchViewLoadingMessage();
    void showGenericDialog(DialogViewModel pMessageModel);
    void showImageDialog(DialogViewModel dialogModel, int resource, boolean closeActivity);
    void showSouvenirWonDialog(String souvenirName, String souvenirDescription, String url);
    void showLoadingDialog(String pLabel);
    void hideLoadingDialog();
    void obtainUserProgress();
    void switchRecarcoinVisible(boolean pVisible);
    void blinkRecarcoin();
    void stopVibrate();
    void showToast(String pText);
    void removeBlinkingAnimation();
    void on2DChestTouch(int pAwait);
    void removeRunnableCallback();
    void deleteModelAR();
    void showNewAchievementDialog(String name, String level, String prize, String score, int resource, boolean navigatePrize);



    void updateIndicators(String pPrizes, int pCoins, String pSouvenirs);
    void updatePrizeButton(int pCoins);

    void onGoldKeyEntered(String pKey, LatLng pLocation, String pAge);
    void onGoldKeyExited(String pKey);
    void onGoldKeyEntered_2D(String pKey, LatLng pLocation, int pAgeID);
    void onGoldPointDataChange(String pKey, LocationPrizeYCRData pGoldPointData);
    void onGoldPointCancelled(DatabaseError pDatabaseError);

    void onSilverKeyEntered(String pKey, LatLng pLocation, String pAge);
    void onSilverKeyEntered_2D(String pKey, LatLng pLocation, int pAgeID);
    void onSilverKeyExited(String pKey);
    void onSilverPointDataChange(String pKey, LocationPrizeYCRData pGoldPointData);
    void onSilverPointCancelled(DatabaseError pDatabaseError);

    void onBronzeKeyEntered(String pKey, LatLng pLocation, String pAge);
    void onBronzeKeyEntered_2D(String pKey, LatLng pLocation, int pAgeID);
    void onBronzeKeyExited(String pKey);
    void onBronzePointDataChange(String pKey, LocationPrizeYCRData pGoldPointData);
    void onBronzePointCancelled(DatabaseError pDatabaseError);

    void onWildcardKeyEntered(String pKey, LatLng pLocation, String pAge);
    void onWildcardKeyEntered_2D(String pKey, LatLng pLocation, int pAgeID);
    void onWildcardKeyExited(String pKey);
    void onWildcardPointDataChange(String pKey, WildcardYCRData pGoldPointData);
    void onWildcardPointCancelled(DatabaseError pDatabaseError);

    void changeToOpenChest(int pChestType, int pEraID);
    void navigateToWildcard();

    void navigateToPrizeDetails();
    void setEnabledChestImage(boolean enabled);
    void startShowcaseAR(boolean accelormeterDevice);
}
