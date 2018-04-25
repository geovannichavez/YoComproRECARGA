package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.firebase.geofire.GeoLocation;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.CapturePrizeInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.CapturePrizeListener;
import com.globalpaysolutions.yocomprorecarga.interactors.FirebasePOIInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.FirebasePOIListener;
import com.globalpaysolutions.yocomprorecarga.location.GoogleLocationApiManager;
import com.globalpaysolutions.yocomprorecarga.location.LocationCallback;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.ExchangeResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Tracking;
import com.globalpaysolutions.yocomprorecarga.models.api.WinPrizeResponse;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.LocationPrizeYCRData;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.WildcardYCRData;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ICapturePrizeARPresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Souvenirs;
import com.globalpaysolutions.yocomprorecarga.ui.activities.SouvenirsGroups;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.CapturePrizeView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josué Chávez on 29/03/2017.
 */

public class CapturePrizeARPResenterImpl implements ICapturePrizeARPresenter, FirebasePOIListener, LocationCallback, CapturePrizeListener
{
    private static final String TAG = CapturePrizeARPResenterImpl.class.getSimpleName();

    private Context mContext;
    private AppCompatActivity mActivity;
    private CapturePrizeInteractor mInteractor;
    private CapturePrizeView mView;

    private GoogleLocationApiManager mGoogleLocationApiManager;
    private FirebasePOIInteractor mFirebaseInteractor;

    private Location mCurrentLocation;
    private UserData mUserData;

    private boolean m3Dcompatible;
    private static String mCurrentChestKey;
    private boolean mIsRunning;

    public CapturePrizeARPResenterImpl(Context pContext, AppCompatActivity pActivity, CapturePrizeView pView)
    {
        mCurrentChestKey = "";

        this.mContext = pContext;
        this.mActivity = pActivity;
        this.mView = pView;
        this.mUserData = UserData.getInstance(mContext);
        this.mInteractor = new CapturePrizeInteractor(mContext, this);
        mIsRunning = false;
    }

    @Override
    public void initialize()
    {
        //Udpate indicators
        mView.updatePrizeButton(UserData.getInstance(mContext).getCurrentCoinsProgress());
        String prizes = String.valueOf(UserData.getInstance(mContext).GetConsumerPrizes());
        String souvs = String.valueOf(UserData.getInstance(mContext).getSavedSouvenirsCount());
        int coins = UserData.getInstance(mContext).getTotalWonCoins();

        mView.updateIndicators(prizes, coins, souvs);

        this.mView.obtainUserProgress();

        mGoogleLocationApiManager = new GoogleLocationApiManager(mActivity, mContext, Constants.ONE_METTER_DISPLACEMENT);
        new initializeGoogleMapsCallback().execute();

        this.mFirebaseInteractor = new FirebasePOIInteractor(mContext, this);

        if (!this.mGoogleLocationApiManager.isConnectionEstablished())
            this.mGoogleLocationApiManager.connect();

        mFirebaseInteractor.initializePOIGeolocation();

        m3Dcompatible = mUserData.Is3DCompatibleDevice();

        mView.switchRecarcoinVisible(false);

        if (m3Dcompatible)
        {
            mView.on3DChestClick();
        }
        else
        {
            mView.onCoinLongClick();
            mView.hideArchViewLoadingMessage();
        }
    }

    @Override
    public void resume()
    {
        if (!this.mGoogleLocationApiManager.isConnectionEstablished())
            this.mGoogleLocationApiManager.connect();

        mFirebaseInteractor.initializePOIGeolocation();
    }

    @Override
    public void prizePointsQuery(LatLng pLocation)
    {
        try
        {
            double radius = (mUserData.Is3DCompatibleDevice()) ? Constants.AR_POI_RADIOS_KM : Constants.RECARSTOP_2D_RADIUS_KM;
            GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
            this.mFirebaseInteractor.goldPointsQuery(location, radius);
            this.mFirebaseInteractor.silverPointsQuery(location, radius);
            this.mFirebaseInteractor.bronzePointsQuery(location, radius);
            this.mFirebaseInteractor.wildcardPointsQuery(location,radius);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void updatePrizePntCriteria(LatLng pLocation)
    {
        double radius = (mUserData.Is3DCompatibleDevice()) ? Constants.AR_POI_RADIOS_KM : Constants.RECARSTOP_2D_RADIUS_KM;
        GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
        this.mFirebaseInteractor.goldPointsUpdateCriteria(location, radius);
        this.mFirebaseInteractor.silverPointsUpdateCriteria(location, radius);
        this.mFirebaseInteractor.bronzePointsUpdateCriteria(location, radius);
    }

    @Override
    public void exchangeCoinsChest(String pArchitectURL)
    {
        try
        {
            synchronized (this)
            {
                if (mIsRunning)
                    return;
            }

            mIsRunning = true;

            Map<String, String> urlMap = getURLMap(pArchitectURL);
            String firebaseID = urlMap.get(Constants.URI_MAP_VALUE_FIREBASE_ID);
            String chestType = urlMap.get(Constants.URI_MAP_VALUE_CHEST_TYPE);
            String latitude = urlMap.get(Constants.URI_MAP_VALUE_LATITUDE);
            String longitude = urlMap.get(Constants.URI_MAP_VALUE_LONGITUDE);
            int chestValue = 0;

            LatLng location = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
            Log.i(TAG, "Chest type: " + chestType + "; FirebaseID: " + firebaseID + "; lat: " + latitude + "; long: " + longitude);

            switch (chestType)
            {
                case Constants.NAME_CHEST_TYPE_GOLD:
                    chestValue = Constants.VALUE_CHEST_TYPE_GOLD;
                    break;
                case Constants.NAME_CHEST_TYPE_SILVER:
                    chestValue = Constants.VALUE_CHEST_TYPE_SILVER;
                    break;
                case Constants.NAME_CHEST_TYPE_BRONZE:
                    chestValue = Constants.VALUE_CHEST_TYPE_BRONZE;
                    break;
                case Constants.NAME_CHEST_TYPE_WILDCARD:
                    chestValue = Constants.VALUE_CHEST_TYPE_WILDCARD;
                    break;
                default:
                    Log.e(TAG, "No chest type found");
                    break;
            }

            if(chestValue == Constants.VALUE_CHEST_TYPE_WILDCARD)
            {
                mUserData.saveLastWildcardTouched(firebaseID, chestValue);
                mView.navigateToWildcard();
            }
            else
            {
                mView.showLoadingDialog(mContext.getString(R.string.label_loading_exchanging_chest));
                mInteractor.openCoinsChest(location, firebaseID, chestValue, mUserData.getEraID());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Crashlytics.logException(ex);
        }
    }

    @Override
    public void exchangeCoinsChest_2D(LatLng pLocation, String pFirebaseID, int pChestType)
    {
        //mView.changeToOpenChest(pChestType, mUserData.getEraID());
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.openCoinsChest(pLocation, pFirebaseID, pChestType, mUserData.getEraID());
    }

    @Override
    public void retrieveUserTracking()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.retrieveConsumerTracking();
    }

    @Override
    public void redeemPrize()
    {
        if(mUserData.getCurrentCoinsProgress() < 20)
        {
            mView.hideLoadingDialog();
            DialogViewModel dialog = new DialogViewModel();
            dialog.setTitle(mContext.getString(R.string.title_not_enough_coins));
            dialog.setLine1(mContext.getString(R.string.label_not_enough_coins));
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));
            mView.showImageDialog(dialog, R.drawable.ic_alert, false);
        }
        else
        {
            mView.showLoadingDialog(mContext.getString(R.string.label_redeeming_prize_wait));
            mInteractor.atemptRedeemPrize();
        }
    }

    @Override
    public void handle2DCoinTouch()
    {
        mView.stopVibrate();
        mView.setEnabledChestImage(false);
        mView.on2DChestTouch(Constants.REQUIRED_TIME_TOUCH_MILLISECONDS, mUserData.getEraID());
    }

    @Override
    public void handleCoinExchangeKeyUp()
    {
        mView.showToast(mContext.getString(R.string.toast_keep_pressed_three_seconds));
        mView.blinkRecarcoin();
        mView.removeRunnableCallback();
    }

    @Override
    public void touchWildcard_2D(String pFirebaseID, int chestType)
    {
        mUserData.saveLastWildcardTouched(pFirebaseID, chestType);
        mView.navigateToWildcard();

    }

    @Override
    public void showcaseARSeen()
    {
        mUserData.setShowcaseARSeen();
    }

    @Override
    public void checkForWelcomeChest()
    {
        if(mUserData.checkWelcomeChestAvailable()) //TODO: Cambiar a true
        {
            double lat = (double) mUserData.getWelcomeChestLat();
            double longt = (double) mUserData.getWelcomeChestLong();
            LatLng location = new LatLng(lat, longt);

            try
            {
                if (m3Dcompatible)
                {
                    mView.onGoldKeyEntered(Constants.WELCOME_CHEST_FIREBASE_KEY, location, mUserData.getEraFolderName());
                }
                else
                {
                    mView.onGoldKeyEntered_2D(Constants.WELCOME_CHEST_FIREBASE_KEY, location, mUserData.getEraID());
                }

                //User has seen the welcom chest, won't be available anymore
                mUserData.setWelcomeChestAvailable(false);
            }
            catch (Exception ex)
            {
                Log.e(TAG, "Error adding welcome chest: " + ex.getMessage());
            }

        }
    }

    @Override
    public void deleteFirstKeySaved()
    {
        try
        {
            UserData.getInstance(mContext).deleteFirstKeyEntered();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void registerKeyEntered(String pKey, LatLng location, int ageID, String chestType)
    {
        try
        {
            //If there is no key, then is the first entered
            if(TextUtils.equals(this.getFirstKeyEntered2D(), ""))
            {
                //Saves first key entered
                this.saveFirstKeyEntered2D(pKey);

                //Draws chests
                switch (chestType)
                {
                    case Constants.NAME_CHEST_TYPE_GOLD:
                        mView.drawChestGold2D(pKey, location, ageID);
                        break;
                    case Constants.NAME_CHEST_TYPE_SILVER:
                        mView.drawChestSilver2D(pKey, location, ageID);
                        break;
                    case Constants.NAME_CHEST_TYPE_BRONZE:
                        mView.drawChestBronze2D(pKey, location, ageID);
                        break;
                    case Constants.NAME_CHEST_TYPE_WILDCARD:
                        mView.drawChestWildcard2D(pKey, location, ageID);
                        break;
                }

                mView.switchRecarcoinVisible(true);
                mView.blinkRecarcoin();
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error registering key: " + ex.getMessage());
        }
    }

    @Override
    public void evaluateSouvsNavigation()
    {
        try
        {

            Intent souvenirs = new Intent(mActivity, Souvenirs.class);

            if(TextUtils.equals(UserData.getInstance(mContext).getEraName(), Constants.ERA_WORLDCUP_NAME)) //WorldCup Era
            {
                souvenirs = new Intent(mActivity, SouvenirsGroups.class);
            }

            mView.navigateSouvenirs(souvenirs);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        try
        {
           if(location != null)
           {
               mView.updateUserLocation(location.getLatitude(), location.getLongitude(), location.getAccuracy());
               mCurrentLocation = location;
           }
        }
        catch (Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void onLocationApiManagerConnected(Location location)
    {
        try
        {
            if(location != null)
            {
                mView.locationManagerConnected(location.getLatitude(), location.getLongitude(), location.getAccuracy());
                mCurrentLocation = location;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onLocationApiManagerDisconnected()
    {

    }

    @Override
    public void gf_goldPoint_onKeyEntered(String pKey, LatLng pLocation, boolean p3DCompatible)
    {
        if(!TextUtils.equals(mUserData.getLastExchangedChestID(), pKey))
        {
            if (p3DCompatible)
            {
                if(!TextUtils.equals(mCurrentChestKey, pKey))
                {
                    mCurrentChestKey = pKey;
                    mView.onGoldKeyEntered(pKey, pLocation, mUserData.getEraFolderName());
                }
            }
            else
            {
                //Draws chest on screen
                mView.onGoldKeyEntered_2D(pKey, pLocation, mUserData.getEraID());

            }
        }
    }

    @Override
    public void gf_goldPoint_onKeyExited(String pKey, boolean p3DCompatible)
    {
        if(!p3DCompatible)
        {
            mView.stopVibrate();
            mView.removeBlinkingAnimation();
            mView.switchRecarcoinVisible(false);

            //Deletes last key entered
            this.deleteSpecificFirstKeyEntered2D(pKey);
        }
        else
        {
            mView.onGoldKeyExited(pKey);
        }
    }

    @Override
    public void gf_goldPoint_onGeoQueryReady()
    {

    }

    @Override
    public void gf_silverPoint_onKeyEntered(String pKey, LatLng pLocation, boolean p3DCompatible)
    {
        //If last exchanged chest, do not draw
        if(!TextUtils.equals(mUserData.getLastExchangedChestID(), pKey))
        {
            if (p3DCompatible)
            {
                if(!TextUtils.equals(mCurrentChestKey, pKey))
                {
                    mCurrentChestKey = pKey;
                    mView.onSilverKeyEntered(pKey, pLocation, mUserData.getEraFolderName());
                }
            }
            else
            {
                //Draws chest on screen
                mView.onSilverKeyEntered_2D(pKey, pLocation, mUserData.getEraID());
            }
        }
    }

    @Override
    public void gf_silverPoint_onKeyExited(String pKey, boolean p3DCompatible)
    {
        mCurrentChestKey = "";

        if(!p3DCompatible)
        {
            mView.stopVibrate();

            mView.removeBlinkingAnimation();
            mView.switchRecarcoinVisible(false);

            //Deletes last key entered
            this.deleteSpecificFirstKeyEntered2D(pKey);
        }
        else
        {
            mView.onSilverKeyExited(pKey);
        }
    }

    @Override
    public void gf_silverPoint_onGeoQueryReady()
    {

    }

    @Override
    public void gf_bronzePoint_onKeyEntered(String pKey, LatLng pLocation, boolean p3DCompatible)
    {
        if(!TextUtils.equals(mUserData.getLastExchangedChestID(), pKey))
        {
            if (p3DCompatible)
            {
                if(!TextUtils.equals(mCurrentChestKey, pKey))
                {
                    mCurrentChestKey = pKey;
                    mView.onBronzeKeyEntered(pKey, pLocation, mUserData.getEraFolderName());
                }
            }
            else
            {
                //Draws chest on screen
                mView.onBronzeKeyEntered_2D(pKey, pLocation, mUserData.getEraID());
            }
        }
    }

    @Override
    public void gf_bronzePoint_onKeyExited(String pKey, boolean p3DCompatible)
    {
        if(!p3DCompatible)
        {
            mView.stopVibrate();

            mView.removeBlinkingAnimation();
            mView.switchRecarcoinVisible(false);

            //Deletes last key entered
            this.deleteSpecificFirstKeyEntered2D(pKey);
        }
        else
        {
            mView.onBronzeKeyExited(pKey);
        }
    }

    @Override
    public void gf_bronzePoint_onGeoQueryReady()
    {

    }

    /*
    *
    *
    *   WILDCARD LISTENER IMPLEMENTATION
    *
    */

    @Override
    public void gf_wildcardPoint_onKeyEntered(String pKey, LatLng pLocation, boolean p3DCompatible)
    {
        if(!TextUtils.equals(mUserData.getLastExchangedChestID(), pKey))
        {
            mView.hideArchViewLoadingMessage();
            if (p3DCompatible)
            {
                if(!TextUtils.equals(mCurrentChestKey, pKey))
                {
                    mCurrentChestKey = pKey;
                    mView.onWildcardKeyEntered(pKey, pLocation, mUserData.getEraFolderName());
                }
            }
            else
            {
                //Draws chest on screen
                mView.onWildcardKeyEntered_2D(pKey, pLocation, mUserData.getEraID());
            }
        }
    }

    @Override
    public void gf_wildcardPoint_onKeyExited(String pKey, boolean p3DCompatible)
    {
       if(!p3DCompatible)
        {
            mView.stopVibrate();

            mView.removeBlinkingAnimation();

            mView.switchRecarcoinVisible(false);

            //Deletes last key entered
            this.deleteSpecificFirstKeyEntered2D(pKey);
        }
        else
       {
           mView.onWildcardKeyExited(pKey);
       }
    }

    @Override
    public void gf_wildcardPoint_onGeoQueryReady()
    {

    }

    @Override
    public void fb_goldPoint_onDataChange(String pKey, LocationPrizeYCRData pGoldPointData)
    {
        mView.onGoldPointDataChange(pKey, pGoldPointData);
    }

    @Override
    public void fb_goldPoint_onCancelled(DatabaseError databaseError)
    {
        mView.onGoldPointCancelled(databaseError);
    }

    @Override
    public void fb_silverPoint_onDataChange(String pKey, LocationPrizeYCRData pSilverPointData)
    {
        mView.onSilverPointDataChange(pKey, pSilverPointData);
    }

    @Override
    public void fb_silverPoint_onCancelled(DatabaseError databaseError)
    {
        mView.onSilverPointCancelled(databaseError);
    }

    @Override
    public void fb_bronzePoint_onDataChange(String pKey, LocationPrizeYCRData pBronzePointData)
    {
        mView.onBronzePointDataChange(pKey, pBronzePointData);
    }

    @Override
    public void fb_bronzePoint_onCancelled(DatabaseError databaseError)
    {
        mView.onBronzePointCancelled(databaseError);
    }

    @Override
    public void fb_wildcardPoint_onDataChange(String pKey, WildcardYCRData wildcardYCRData)
    {
        mView.onWildcardPointDataChange(pKey, wildcardYCRData);
    }

    @Override
    public void fb_wildcardPoint_onCancelled(DatabaseError databaseError)
    {
        mView.onWildcardPointCancelled(databaseError);
    }

    @Override
    public void detachFirebaseListeners()
    {
        mFirebaseInteractor.detachFirebaseListeners();
    }

    @Override
    public void onRetrieveTracking(Tracking pTracking)
    {
        mView.hideLoadingDialog();

        //Saves updated nickname
        mUserData.saveNickname(pTracking.getNickname());

        mInteractor.saveUserTracking(pTracking);
        mView.updateIndicators(String.valueOf(pTracking.getTotalWinPrizes()),
                mUserData.getTotalWonCoins(),
                String.valueOf(pTracking.getTotalSouvenirs()));
        mView.updatePrizeButton(pTracking.getCurrentCoinsProgress());

        if(!mUserData.showcaseARSeen())
        {
            mView.startShowcaseAR(m3Dcompatible);
        }
    }

    @Override
    public void onTrackingError(int pCodeStatus, Throwable pThrowable, String pRequiredVersion)
    {
        try
        {
            mView.hideLoadingDialog();

            if(pCodeStatus == 426)
            {
                DialogViewModel dialog = new DialogViewModel();
                dialog.setTitle(mContext.getString(R.string.title_update_required));
                dialog.setLine1(String.format(mContext.getString(R.string.content_update_required), pRequiredVersion));
                dialog.setAcceptButton(mContext.getResources().getString(R.string.button_accept));
                mView.showGenericDialog(dialog);
            }
            else
            {
                int coins = mUserData.getTotalWonCoins();
                int prizes = mUserData.GetConsumerPrizes();
                int souvenirs = mUserData.getSavedSouvenirsCount();
                int coinsProgress = mUserData.GetUserCurrentCoinsProgress();
                mView.updateIndicators(String.valueOf(prizes), coins, String.valueOf(souvenirs));
                mView.updatePrizeButton(coinsProgress);

                DialogViewModel dialog = new DialogViewModel();
                dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
                dialog.setLine1(mContext.getString(R.string.error_content_progress_something_went_wrong_try_again));
                dialog.setAcceptButton(mContext.getResources().getString(R.string.button_accept));
                mView.showGenericDialog(dialog);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void onOpenChestSuccess(ExchangeResponse pExchangeResponse, int pChestType, String firebaseID)
    {
        DialogViewModel dialog = new DialogViewModel();
        mView.hideLoadingDialog();
        mView.setEnabledChestImage(true);
        mIsRunning = false;

        //Saves last exchanged chest's FirebaseID
        mUserData.saveLastExchangedChestID(firebaseID);

       try
       {
           if(pExchangeResponse.getTracking() != null)
                mInteractor.saveUserTracking(pExchangeResponse.getTracking());


          mUserData.saveLastChestValue(pExchangeResponse.getExchangeCoins());

           if(pExchangeResponse.getAchievement() != null)
                mUserData.saveLastAchievement(pExchangeResponse.getAchievement());

           if(pExchangeResponse.getType() == Constants.CHEST_COINS_TYPE)
           {
               if(!TextUtils.equals(pExchangeResponse.getCode(), "05"))
               {
                   mView.updateIndicators(String.valueOf(pExchangeResponse.getTracking().getTotalWinPrizes()),
                           mUserData.getTotalWonCoins(),
                           String.valueOf(pExchangeResponse.getTracking().getTotalSouvenirs()));
                   mView.updatePrizeButton(pExchangeResponse.getTracking().getCurrentCoinsProgress());

                   dialog.setTitle(mContext.getString(R.string.label_congratulations_title));
                   dialog.setLine1(String.format(mContext.getString(R.string.label_chest_open_succesfully), String.valueOf(mUserData.getLastChestExchangedValue())));
                   dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                   mView.showImageDialog(dialog, R.drawable.img_recarcoin_multiple, true); //boolean value, flag for closing activity or not

                   //Checks for achievemnts and creates dialog
                   if(pExchangeResponse.getAchievement() != null)
                   {
                       String name = pExchangeResponse.getAchievement().getName();
                       String level = String.valueOf(pExchangeResponse.getAchievement().getLevel());
                       String prize = String.valueOf(pExchangeResponse.getAchievement().getPrize());
                       String score = String.valueOf(pExchangeResponse.getAchievement().getScore());

                       int resource;
                       if(pExchangeResponse.getAchievement().getLevel() == 1)
                            resource = R.drawable.ic_achvs_counter_1;
                       else if (pExchangeResponse.getAchievement().getLevel() == 2)
                           resource = R.drawable.ic_achvs_counter_2;
                       else if (pExchangeResponse.getAchievement().getLevel() == 3)
                           resource = R.drawable.ic_achvs_counter_3;
                       else
                           resource = R.drawable.ic_achvs_counter_0;

                        mView.showNewAchievementDialog(name, level, prize, score, resource, false);
                   }
               }
               else
               {
                   dialog.setTitle(mContext.getString(R.string.label_alreadey_open_chest_title));
                   dialog.setLine1(mContext.getString(R.string.label_allowed_open_chest_once_per_day));
                   dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                   mView.showGenericDialog(dialog);
               }
           }
           else
           {
               if (!TextUtils.equals(pExchangeResponse.getCode(), "05"))
               {
                   mUserData.saveSouvenirObtained(pExchangeResponse.getTitle(), pExchangeResponse.getDescription(), pExchangeResponse.getImgUrl(), pExchangeResponse.getValue());

                   String prizes = String.valueOf(pExchangeResponse.getTracking().getTotalWinPrizes());
                   int coins = pExchangeResponse.getTracking().getTotalWinCoins();
                   String souvs = String.valueOf(pExchangeResponse.getTracking().getTotalSouvenirs());
                   mView.updateIndicators(prizes, coins, souvs);

                   mView.showSouvenirWonDialog(pExchangeResponse.getTitle(), pExchangeResponse.getDescription(), pExchangeResponse.getImgUrl());

                   //Checks for achievemnts and creates dialog
                   if (pExchangeResponse.getAchievement() != null)
                   {
                       String name = pExchangeResponse.getAchievement().getName();
                       String level = String.valueOf(pExchangeResponse.getAchievement().getLevel());
                       String prize = String.valueOf(pExchangeResponse.getAchievement().getPrize());
                       String score = String.valueOf(pExchangeResponse.getAchievement().getScore());

                       int resource;
                       if (pExchangeResponse.getAchievement().getLevel() == 1)
                           resource = R.drawable.ic_achvs_counter_1;
                       else if (pExchangeResponse.getAchievement().getLevel() == 2)
                           resource = R.drawable.ic_achvs_counter_2;
                       else if (pExchangeResponse.getAchievement().getLevel() == 3)
                           resource = R.drawable.ic_achvs_counter_3;
                       else
                           resource = R.drawable.ic_achvs_counter_0;

                       mView.showNewAchievementDialog(name, level, prize, score, resource, false);
                   }
               }
               else
               {
                   dialog.setTitle(mContext.getString(R.string.label_alreadey_open_chest_title));
                   dialog.setLine1(mContext.getString(R.string.label_allowed_open_chest_once_per_day));
                   dialog.setAcceptButton(mContext.getString(R.string.button_accept));
                   mView.showGenericDialog(dialog);
               }
           }

           //Removes Image and updates UI
           if (!mUserData.Is3DCompatibleDevice())
           {
               mView.removeBlinkingAnimation();
               mView.switchRecarcoinVisible(false);
           }
           else
           {
               mView.deleteModelAR();
           }
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
           Crashlytics.logException(ex);
           Log.e(TAG, "Happened from handling data for onOpenChestSuccess");
           dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
           dialog.setLine1(mContext.getString(R.string.error_content_something_went_wrong_try_again));
           dialog.setAcceptButton(mContext.getResources().getString(R.string.button_accept));
           mView.showGenericDialog(dialog);

           if(!mUserData.Is3DCompatibleDevice())
           {
               mView.removeBlinkingAnimation();
               mView.switchRecarcoinVisible(false);
           }
       }
    }

    @Override
    public void onOpenChestError(int pCodeStatus, Throwable pThrowable, String requiredVersion)
    {
        try
        {
            mView.hideLoadingDialog();
            mView.setEnabledChestImage(true);
            processErrorMessage(pCodeStatus, pThrowable, requiredVersion);
            mIsRunning = false;

            if(!mUserData.Is3DCompatibleDevice())
            {
                this.mView.obtainUserProgress();
                mView.switchRecarcoinVisible(false);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRedeemPrizeSuccess(WinPrizeResponse pResponse)
    {

        DialogViewModel dialog = new DialogViewModel();
        mView.hideLoadingDialog();

        //if(pResponse.getWaitTime() == null || pResponse.getTracking() != null)
        if(TextUtils.equals(pResponse.getResponseCode(), "00"))
        {
            //Saves tracking and updates UI
            if(pResponse.getTracking() != null)
                mInteractor.saveUserTracking(pResponse.getTracking());

            //Updates indicators
            mView.updateIndicators(String.valueOf(pResponse.getTracking().getTotalWinPrizes()),
                    mUserData.getTotalWonCoins(),
                    String.valueOf(pResponse.getTracking().getTotalSouvenirs()));
            mView.updatePrizeButton(pResponse.getTracking().getCurrentCoinsProgress());

            //Saves last saved prize
            mUserData.saveLastPrizeTitle(pResponse.getTitle());
            mUserData.saveLastPrizeDescription(pResponse.getDescription());
            mUserData.saveLastPrizeCode(pResponse.getCode());
            mUserData.saveLastPrizeDial(pResponse.getDial());
            mUserData.saveLastPrizeLevel(pResponse.getPrizeLevel());
            mUserData.saveLastPrizeLogoUrl(pResponse.getLogoUrl());
            mUserData.saveLastPrizeExchangedColor(pResponse.getHexColor());

            if(pResponse.getAchievement() != null)
            {
                mUserData.saveLastAchievement(pResponse.getAchievement());

                String name = pResponse.getAchievement().getName();
                String level = String.valueOf(pResponse.getAchievement().getLevel());
                String prize = String.valueOf(pResponse.getAchievement().getPrize());
                String score = String.valueOf(pResponse.getAchievement().getScore());

                int resource;
                if (pResponse.getAchievement().getLevel() == 1)
                    resource = R.drawable.ic_achvs_counter_1;
                else if (pResponse.getAchievement().getLevel() == 2)
                    resource = R.drawable.ic_achvs_counter_2;
                else if (pResponse.getAchievement().getLevel() == 3)
                    resource = R.drawable.ic_achvs_counter_3;
                else
                    resource = R.drawable.ic_achvs_counter_0;

                mView.showNewAchievementDialog(name, level, prize, score, resource, true); //Tells if clicking 'Close' is takin user to PrizeDetails
            }
            else
            {

                //Navigates to prize details
                mView.navigateToPrizeDetails();
            }
        }
        else
        {
            mUserData.saveAwaitTime(pResponse.getWaitTime());
            dialog.setTitle(mContext.getString(R.string.cant_redeem_title));
            dialog.setLine1(String.format(mContext.getString(R.string.redeem_prize_interval), mUserData.getAwaitTimePending()));
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));
            mView.showImageDialog(dialog, R.drawable.ic_alert, false);
        }


    }

    @Override
    public void onRedeemPrizeError(int pCodeStatus, Throwable pThrowable, String requiredVersion)
    {
        mView.hideLoadingDialog();
        mView.blinkRecarcoin();
        processErrorMessage(pCodeStatus, pThrowable, requiredVersion);
    }


    /*
    *
    *
    *   OTHER METHODS
    *
    *
    */
    private Map<String, String> getURLMap(String url)
    {
        String[] params = url.split("//");
        Map<String, String> map = new HashMap<>();
        map.put(Constants.URI_MAP_VALUE_CHEST_TYPE, params[1]);
        map.put(Constants.URI_MAP_VALUE_FIREBASE_ID, params[2]);
        map.put(Constants.URI_MAP_VALUE_LATITUDE, params[3]);
        map.put(Constants.URI_MAP_VALUE_LONGITUDE, params[4]);

        return map;
    }

    private void saveFirstKeyEntered2D(String key)
    {
        try
        {
            UserData.getInstance(mContext).saveFirstKeyEntered(key);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error saving first key entered: " + ex.getMessage());
        }
    }

    private String getFirstKeyEntered2D()
    {
        return UserData.getInstance(mContext).getFirstKeyEntered();
    }

    private void deleteSpecificFirstKeyEntered2D(String key)
    {
        try
        {
            //Deletes key saved only if is the same that has left
            if(TextUtils.equals(this.getFirstKeyEntered2D(), key))
            {
                UserData.getInstance(mContext).deleteFirstKeyEntered();
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error deleting key from shared preferences: " +ex.getMessage());
        }
    }

    private void processErrorMessage(int pCodeStatus, Throwable pThrowable, String requiredVersion)
    {
        DialogViewModel errorResponse = new DialogViewModel();

        try
        {
            String Titulo;
            String Linea1;
            String Button;

            if (pThrowable != null)
            {
                if (pThrowable instanceof SocketTimeoutException)
                {
                    Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = mContext.getString(R.string.button_accept);
                }
                else if (pThrowable instanceof IOException)
                {
                    Titulo = mContext.getString(R.string.error_title_internet_connecttion);
                    Linea1 = mContext.getString(R.string.error_content_internet_connecttion);
                    Button = mContext.getString(R.string.button_accept);
                }
                else
                {
                    Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = mContext.getString(R.string.button_accept);
                }
            }
            else
            {
                if(pCodeStatus == 401)
                {
                    Titulo = mContext.getString(R.string.error_title_vendor_not_found);
                    Linea1 = mContext.getString(R.string.error_content_vendor_not_found_line);
                    Button = mContext.getString(R.string.button_accept);
                }
                else if (pCodeStatus == 426)
                {
                    Titulo = mContext.getString(R.string.title_update_required);
                    Linea1 = String.format(mContext.getString(R.string.content_update_required), requiredVersion);
                    Button = mContext.getString(R.string.button_accept);
                }
                else
                {
                    Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = mContext.getString(R.string.button_accept);
                }
            }

            errorResponse.setTitle(Titulo);
            errorResponse.setLine1(Linea1);
            errorResponse.setAcceptButton(Button);
            this.mView.showGenericDialog(errorResponse);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /*
    *
    *
    *
    *   ASYNC TASKS
    *
    *
    */

    private class initializeGoogleMapsCallback extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            mGoogleLocationApiManager.setLocationCallback(CapturePrizeARPResenterImpl.this);
            return null;
        }
    }

}
