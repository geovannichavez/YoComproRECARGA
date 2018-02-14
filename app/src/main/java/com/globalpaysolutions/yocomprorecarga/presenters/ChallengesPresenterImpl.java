package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.geofire.GeoLocation;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.ChallengesInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.ChallengesListener;
import com.globalpaysolutions.yocomprorecarga.location.GoogleLocationApiManager;
import com.globalpaysolutions.yocomprorecarga.location.LocationCallback;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ChallengesResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IChallengesPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.ChallengesView;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Josué Chávez on 8/2/2018.
 */

public class ChallengesPresenterImpl implements IChallengesPresenter, ChallengesListener, LocationCallback
{
    private static final String TAG = ChallengesPresenterImpl.class.getSimpleName();

    private Context mContext;
    private ChallengesView mView;
    private ChallengesInteractor mInteractor;
    private AppCompatActivity mActivity;

    private GoogleLocationApiManager mGoogleLocationApiManager;


    public ChallengesPresenterImpl(Context context, ChallengesView view, AppCompatActivity activity)
    {
        this.mContext = context;
        this.mView = view;
        this.mInteractor = new ChallengesInteractor(mContext);
        this.mActivity = activity;
    }


    @Override
    public void retrieveChallenges()
    {
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.retrieveChallenges(this);
    }

    @Override
    public void initialize()
    {
        boolean locationVisible = UserData.getInstance(mContext).checkCurrentLocationVisible();

        mView.initializeViews(locationVisible);

        if(locationVisible)
        {
            //Creating Google Location Manager
            this.mGoogleLocationApiManager = new GoogleLocationApiManager(mActivity, mContext, Constants.FOUR_METTERS_DISPLACEMENT);
            this.mGoogleLocationApiManager.setLocationCallback(this);
        }
    }

    @Override
    public void locationVisible(boolean visible)
    {
        try
        {
            if(visible)
            {
                //Connects to location service
                this.mGoogleLocationApiManager.connect();
            }
            else
            {
                if(mGoogleLocationApiManager != null)
                    mGoogleLocationApiManager.disconnect();
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on make location visble: " + ex.getMessage());
        }
    }

    /*
    *
    *
    *   Challenges Listener
    *
    *
    */

    @Override
    public void onRetrieveSuccess(ChallengesResponse response)
    {
        mView.hideLoadingDialog();
        if(response != null)
            mView.renderChallegenes(response.getList());
    }

    @Override
    public void onRetrieveError(int codeStatus, Throwable throwable, String requiredVersion, SimpleResponse errorResponse)
    {
        mView.hideLoadingDialog();
    }

    @Override
    public void onPlayerDataInserted(String playerFacebookID, LatLng location)
    {
        GeoLocation geoLocation = new GeoLocation(location.latitude, location.longitude);
        mInteractor.setPlayerLocation(playerFacebookID, geoLocation, this);
    }

    @Override
    public void onPlayerLocationSet(String key)
    {

    }

    @Override
    public void onPlayerLocationDeleted(String key)
    {

    }


    /*
    *
    *
    * LocationCallback
    *
    *
    */

    @Override
    public void onLocationChanged(Location location)
    {
        try
        {
            if(location != null)
            {
                String firebaseKey = UserData.getInstance(mContext).getFacebookProfileId();
                GeoLocation userLocation = new GeoLocation(location.getLatitude(), location.getLongitude());
                mInteractor.setPlayerLocation(firebaseKey, userLocation, this);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on location chnaged: " + ex.getMessage());
        }
    }

    @Override
    public void onLocationApiManagerConnected(Location location)
    {
        try
        {
            if(location != null)
            {
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mInteractor.writePlayerDataLocation(userLocation, this);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on connected to Google Location API: " + ex.getMessage());
        }
    }
}
