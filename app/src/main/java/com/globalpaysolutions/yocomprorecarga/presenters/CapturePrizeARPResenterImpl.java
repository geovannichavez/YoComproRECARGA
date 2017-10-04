package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ICapturePrizeARPresenter;
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

    public CapturePrizeARPResenterImpl(Context pContext, AppCompatActivity pActivity, CapturePrizeView pView)
    {
        this.mContext = pContext;
        this.mActivity = pActivity;
        this.mView = pView;
        this.mUserData = UserData.getInstance(mContext);
        this.mInteractor = new CapturePrizeInteractor(mContext, this);
    }

    @Override
    public void initialize()
    {
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
            mView.onPOIClick();
        }
        else
        {
            mView.onCoinLongClick();
            mView.hideArchViewLoadingMessage();
            mView.makeVibrate(Constants.OUT_RADIUS_VIBRATION_TIME_MILLISECONDS, Constants.OUT_RADIUS_VIBRATION_SLEEP_MILLISECONDS);
        }
    }

    @Override
    public void resume()
    {
        if (!this.mGoogleLocationApiManager.isConnectionEstablished())
            this.mGoogleLocationApiManager.connect();

        mFirebaseInteractor.initializePOIGeolocation();

        if (!m3Dcompatible)
        {
            mView.makeVibrate(Constants.OUT_RADIUS_VIBRATION_TIME_MILLISECONDS, Constants.OUT_RADIUS_VIBRATION_SLEEP_MILLISECONDS);
            mView.removeBlinkingAnimation();
            mView.switchRecarcoinVisible(false);
        }
    }

    @Override
    public void prizePointsQuery(LatLng pLocation)
    {
        double radius = (mUserData.Is3DCompatibleDevice()) ? Constants.AR_POI_RADIOS_KM : Constants.RECARSTOP_2D_RADIUS_KM;
        GeoLocation location = new GeoLocation(pLocation.latitude, pLocation.longitude);
        this.mFirebaseInteractor.goldPointsQuery(location, radius);
        this.mFirebaseInteractor.silverPointsQuery(location, radius);
        this.mFirebaseInteractor.bronzePointsQuery(location, radius);
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

    public void _genericPOIAction(String pDisplayText)
    {
        DialogViewModel dialog = new DialogViewModel();
        dialog.setTitle(String.format("Punto de %1$s", pDisplayText));
        dialog.setLine1(String.format("Ha tocado el Punto de %1$s", pDisplayText));
        dialog.setAcceptButton(mContext.getResources().getString(R.string.button_accept));
        mView.showGenericDialog(dialog);
    }

    @Override
    public void exchangeCoinsChest(String pArchitectURL)
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_exchanging_chest));
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
            default:
                Log.e(TAG, "No chest type found");
                break;
        }

        mInteractor.exchangePrizeData(location, firebaseID, chestValue);
    }

    @Override
    public void exchangeCoinsChest_2D(LatLng pLocation, String pFirebaseID, int pChestType)
    {
        mInteractor.exchangePrizeData(pLocation, pFirebaseID, pChestType);
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
        mView.showLoadingDialog(mContext.getString(R.string.label_redeeming_prize_wait));
        if(mUserData.getCurrentCoinsProgress() < 20)
        {
            mView.hideLoadingDialog();
            DialogViewModel dialog = new DialogViewModel();
            dialog.setTitle(mContext.getString(R.string.title_not_enough_coins));
            dialog.setLine1(mContext.getString(R.string.label_not_enough_coins));
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));
            mView.showGenericDialog(dialog);
        }
        else
        {
            mInteractor.atemptRedeemPrize();
        }
    }

    @Override
    public void handleCoinTouch()
    {
        mView.stopVibrate();
        mView.onChestTouch(Constants.REQUIRED_TIME_TOUCH_MILLISECONDS);
    }

    @Override
    public void handleCoinExchangeKeyUp()
    {
        mView.showToast(mContext.getString(R.string.toast_keep_pressed_three_seconds));
        mView.makeVibrate(Constants.ONRADIUS_VIBRATION_TIME_MILLISECONDS, Constants.ONRADIUS_VIBRATION_SLEEP_MILLISECONDS);
        mView.blinkRecarcoin();
        mView.removeRunnableCallback();
    }

    @Override
    public void onLocationChanged(Location location)
    {
        mView.updateUserLocation(location.getLatitude(), location.getLongitude(), location.getAccuracy());
        mCurrentLocation = location;
    }

    @Override
    public void onLocationApiManagerConnected(Location location)
    {
        try
        {
            mView.locationManagerConnected(location.getLatitude(), location.getLongitude(), location.getAccuracy());
            mCurrentLocation = location;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void gf_goldPoint_onKeyEntered(String pKey, LatLng pLocation, boolean p3DCompatible)
    {
        if (p3DCompatible)
        {
            mView.onGoldKeyEntered(pKey, pLocation);
        }
        else
        {
            mView.hideArchViewLoadingMessage();
            mView.onGoldKeyEntered_2D(pKey, pLocation);
            mView.switchRecarcoinVisible(true);
            mView.blinkRecarcoin();
            mView.makeVibrate(Constants.ONRADIUS_VIBRATION_TIME_MILLISECONDS, Constants.ONRADIUS_VIBRATION_SLEEP_MILLISECONDS);
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
            mView.makeVibrate(Constants.OUT_RADIUS_VIBRATION_TIME_MILLISECONDS, Constants.OUT_RADIUS_VIBRATION_SLEEP_MILLISECONDS);
            //Game UX
            mView.showToast(mContext.getString(R.string.toast_gold_out_of_search_range));
        }
    }

    @Override
    public void gf_silverPoint_onKeyEntered(String pKey, LatLng pLocation, boolean p3DCompatible)
    {
        if (p3DCompatible)
        {
            mView.onSilverKeyEntered(pKey, pLocation);
        }
        else
        {
            mView.hideArchViewLoadingMessage();
            mView.onSilverKeyEntered_2D(pKey, pLocation);
            mView.switchRecarcoinVisible(true);
            mView.blinkRecarcoin();
            mView.makeVibrate(Constants.ONRADIUS_VIBRATION_TIME_MILLISECONDS, Constants.ONRADIUS_VIBRATION_SLEEP_MILLISECONDS);
        }

    }

    @Override
    public void gf_silverPoint_onKeyExited(String pKey, boolean p3DCompatible)
    {
        if(!p3DCompatible)
        {
            mView.stopVibrate();

            mView.removeBlinkingAnimation();
            mView.onSilverKeyExited(pKey);
            mView.switchRecarcoinVisible(false);
            mView.makeVibrate(Constants.OUT_RADIUS_VIBRATION_TIME_MILLISECONDS, Constants.OUT_RADIUS_VIBRATION_SLEEP_MILLISECONDS);
            //Game UX
            mView.showToast(mContext.getString(R.string.toast_silver_out_of_search_range));
        }
    }

    @Override
    public void gf_bronzePoint_onKeyEntered(String pKey, LatLng pLocation, boolean p3DCompatible)
    {
        if (p3DCompatible)
        {
            mView.onBronzeKeyEntered(pKey, pLocation);
        }
        else
        {
            mView.hideArchViewLoadingMessage();
            mView.onBronzeKeyEntered_2D(pKey, pLocation);
            mView.switchRecarcoinVisible(true);
            mView.blinkRecarcoin();
            mView.makeVibrate(Constants.ONRADIUS_VIBRATION_TIME_MILLISECONDS, Constants.ONRADIUS_VIBRATION_SLEEP_MILLISECONDS);
        }
    }

    @Override
    public void gf_bronzePoint_onKeyExited(String pKey, boolean p3DCompatible)
    {
        if(!p3DCompatible)
        {
            mView.stopVibrate();

            mView.removeBlinkingAnimation();
            mView.onBronzeKeyExited(pKey);
            mView.switchRecarcoinVisible(false);
            mView.makeVibrate(Constants.OUT_RADIUS_VIBRATION_TIME_MILLISECONDS, Constants.OUT_RADIUS_VIBRATION_SLEEP_MILLISECONDS);
            //Game UX
            mView.showToast(mContext.getString(R.string.toast_bronze_out_of_search_range));
        }
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
    public void onRetrieveTracking(Tracking pTracking)
    {
        mView.hideLoadingDialog();
        mInteractor.saveUserTracking(pTracking);
        mView.updateIndicators(String.valueOf(pTracking.getTotalWinPrizes()), String.valueOf(pTracking.getTotalWinCoins()));
        mView.updatePrizeButton(pTracking.getCurrentCoinsProgress());
    }

    @Override
    public void onTrackingError(int pCodeStatus, Throwable pThrowable)
    {
        mView.hideLoadingDialog();
        int coins = mUserData.GetConsumerCoins();
        int prizes = mUserData.GetConsumerPrizes();
        int coinsProgress = mUserData.GetUserCurrentCoinsProgress();
        mView.updateIndicators(String.valueOf(prizes), String.valueOf(coins));
        mView.updatePrizeButton(coinsProgress);

        DialogViewModel dialog = new DialogViewModel();
        dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
        dialog.setLine1(mContext.getString(R.string.error_content_progress_something_went_wrong_try_again));
        dialog.setAcceptButton(mContext.getResources().getString(R.string.button_accept));
        mView.showGenericDialog(dialog);

    }

    @Override
    public void onExchangeChestSuccess(ExchangeResponse pExchangeResponse)
    {
        DialogViewModel dialog = new DialogViewModel();
        mView.hideLoadingDialog();

        if(pExchangeResponse.getExchangeCoins() != null && pExchangeResponse.getExchangeCoins() > 0)
        {
            mInteractor.saveUserTracking(pExchangeResponse.getTracking());
            mUserData.saveLastChestValue(pExchangeResponse.getExchangeCoins());
            mView.updateIndicators(String.valueOf(pExchangeResponse.getTracking().getTotalWinPrizes()), String.valueOf(pExchangeResponse.getTracking().getTotalWinCoins()));
            mView.updatePrizeButton(pExchangeResponse.getTracking().getCurrentCoinsProgress());

            dialog.setTitle(mContext.getString(R.string.label_congratulations_title));
            dialog.setLine1(String.format(mContext.getString(R.string.label_chest_open_succesfully), String.valueOf(mUserData.getLastChestExchangedValue())));
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));
        }
        else
        {
            dialog.setTitle(mContext.getString(R.string.label_alreadey_open_chest_title));
            dialog.setLine1(mContext.getString(R.string.label_allowed_open_chest_once_per_day));
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));
        }

        //Removes Image and updates UI
        if(!mUserData.Is3DCompatibleDevice())
        {
            mView.removeBlinkingAnimation();
            mView.switchRecarcoinVisible(false);
        }

        mView.showGenericDialog(dialog);
    }

    @Override
    public void onExchangeError(int pCodeStatus, Throwable pThrowable)
    {
        mView.hideLoadingDialog();
        processErrorMessage(pCodeStatus, pThrowable);
    }

    @Override
    public void onRedeemPrizeSuccess(WinPrizeResponse pResponse)
    {

        DialogViewModel dialog = new DialogViewModel();
        mView.hideLoadingDialog();

        if(pResponse.getWaitTime() == null || pResponse.getTracking() != null)
        {
            //Saves tracking and updates UI
            mInteractor.saveUserTracking(pResponse.getTracking());
            mView.updateIndicators(String.valueOf(pResponse.getTracking().getTotalWinPrizes()), String.valueOf(pResponse.getTracking().getTotalWinCoins()));
            mView.updatePrizeButton(pResponse.getTracking().getCurrentCoinsProgress());

            //Saves last saved prize
            mUserData.saveLastPrizeTitle(pResponse.getTitle());
            mUserData.saveLastPrizeDescription(pResponse.getDescription());
            mUserData.saveLastPrizeCode(pResponse.getCode());
            mUserData.saveLastPrizeDial(pResponse.getDial());
            mUserData.saveLastPrizeLevel(pResponse.getPrizeLevel());

            //Shows data on UI
            dialog.setTitle(mContext.getString(R.string.label_congratulations_title));
            dialog.setLine1(mContext.getString(R.string.label_click_on_button_to_redeem));
            dialog.setAcceptButton(mContext.getString(R.string.button_redeem_now));
            mView.showPrizeColectedDialog(dialog);
        }
        else
        {
            mUserData.saveAwaitTime(pResponse.getWaitTime());
            dialog.setTitle(mContext.getString(R.string.cant_redeem_title));
            dialog.setLine1(String.format(mContext.getString(R.string.redeem_prize_interval), mUserData.getAwaitTimePending()));
            dialog.setAcceptButton(mContext.getString(R.string.button_accept));
            mView.showGenericDialog(dialog);
        }


    }

    @Override
    public void onRedeemPrizeError(int pCodeStatus, Throwable pThrowable)
    {
        processErrorMessage(pCodeStatus, pThrowable);
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

    private void processErrorMessage(int pCodeStatus, Throwable pThrowable)
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
