package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;

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
import com.globalpaysolutions.yocomprorecarga.models.api.TrackingResponse;
import com.globalpaysolutions.yocomprorecarga.models.geofire_data.LocationPrizeYCRData;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ICapturePrizeARPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.CapturePrizeView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseError;

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
        this.mUserData = new UserData(mContext);
        this.mInteractor = new CapturePrizeInteractor(mContext, this);
    }

    @Override
    public void initialize()
    {
        this.mView.obtainUserProgress();
        this.mGoogleLocationApiManager = new GoogleLocationApiManager(mActivity, mContext);
        this.mGoogleLocationApiManager.setLocationCallback(this);

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

    @Override
    public void _genericPOIAction(String pDisplayText)
    {
        DialogViewModel dialog = new DialogViewModel();
        dialog.setTitle(String.format("Punto de %1$s", pDisplayText));
        dialog.setLine1(String.format("Ha tocado el Punto de %1$s", pDisplayText));
        dialog.setAcceptButton(mContext.getResources().getString(R.string.button_accept));
        mView.showGenericDialog(dialog);
    }

    @Override
    public void attemptExchangeCoin(String pFirebaseID)
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        LatLng currentLocation = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        mInteractor.exchangePrizeData(currentLocation, pFirebaseID, mUserData.GetConsumerID());
    }

    @Override
    public void _navigateToPrize()
    {
        mView.navigatePrizeDetail();
    }

    @Override
    public void retrieveUserTracking()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.retrieveConsumerTracking();
    }

    @Override
    public void handleCoinTouch()
    {
        mView.onCoinTouch(Constants.REQUIRED_TIME_TOUCH_MILLISECONDS);
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
        mView.locationManagerConnected(location.getLatitude(), location.getLongitude(), location.getAccuracy());
        mCurrentLocation = location;
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
            //TODO: Para pruebas
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
            //TODO: Para pruebas
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
            //TODO: Para pruebas
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
    public void onRetrieveTracking(TrackingResponse pTracking)
    {
        mView.hideLoadingDialog();
        mInteractor.saveUserTracking(pTracking);
        mView.updateIndicators(String.valueOf(pTracking.getTotalWinPrizes()), String.valueOf(pTracking.getTotalWinCoins()));
        mView.changeBar(pTracking.getCurrentCoinsProgress());
    }

    @Override
    public void onTrackingError(int pCodeStatus, Throwable pThrowable)
    {
        mView.hideLoadingDialog();
        int coins = mUserData.GetConsumerCoins();
        int prizes = mUserData.GetConsumerPrizes();
        int coinsProgress = mUserData.GetUserCurrentCoinsProgress();
        mView.updateIndicators(String.valueOf(prizes), String.valueOf(coins));
        mView.changeBar(coinsProgress);

        DialogViewModel dialog = new DialogViewModel();
        dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
        dialog.setLine1(mContext.getString(R.string.error_content_progress_something_went_wrong_try_again));
        dialog.setAcceptButton(mContext.getResources().getString(R.string.button_accept));
        mView.showGenericDialog(dialog);

    }

    @Override
    public void onExchangeCoinSuccess(ExchangeResponse pExchangeResponse)
    {
        mView.hideLoadingDialog();
        //mView.changeBar(pExchangeResponse.);
    }

    @Override
    public void onExchangeError(int pCodeStatus, Throwable pThrowable)
    {
        mView.hideLoadingDialog();
    }

}
