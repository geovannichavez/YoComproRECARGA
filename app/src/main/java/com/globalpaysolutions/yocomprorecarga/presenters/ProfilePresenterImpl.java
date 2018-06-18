package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.Profile;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.ProfileInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.ProfileListener;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.Tracking;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IProfilePresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Souvenirs;
import com.globalpaysolutions.yocomprorecarga.ui.activities.SouvenirsGroups;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.StringsURL;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.ProfileView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.net.UnknownServiceException;

/**
 * Created by Josué Chávez on 17/07/2017.
 */

public class ProfilePresenterImpl implements IProfilePresenter, ProfileListener
{
    private static final String TAG = ProfilePresenterImpl.class.getSimpleName();

    private Context mContext;
    private AppCompatActivity mActivity;
    private ProfileView mView;
    private UserData mUserData;
    private ProfileInteractor mInteractor;

    public ProfilePresenterImpl(Context context, AppCompatActivity activity, ProfileView view)
    {
        this.mContext = context;
        this.mActivity = activity;
        this.mView = view;
        this.mUserData = UserData.getInstance(mContext);
        this.mInteractor = new ProfileInteractor(mContext);
    }

    @Override
    public void retrieveTracking()
    {
        mInteractor.retrieveTracking(this);
    }

    @Override
    public void loadInitialData()
    {
        mView.updateIndicators(String.valueOf(mUserData.getTotalWonCoins()), String.valueOf(mUserData.getSavedSouvenirsCount()));

        if (TextUtils.equals(mUserData.getEraName(), Constants.ERA_WORLDCUP_NAME))
        {
            if (!TextUtils.isEmpty(mUserData.getWorldcupCountryName()))
                mView.loadCountryBadge(mUserData.getWorldcupCountryUrl());
        }

        switch (UserData.getInstance(mContext).getAuthModeSelected())
        {
            case Constants.FACEBOOK:
                Profile profile = Profile.getCurrentProfile();
                if (profile != null)
                {
                    mView.loadViewsState("", mUserData.getNickname(),
                            profile.getProfilePictureUri(500, 500).toString());
                }
                else
                {
                    mView.loadViewsState("", mUserData.getNickname(), null);
                }
                break;
            case Constants.GOOGLE:
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mContext);
                if (account != null)
                {
                    mView.loadViewsState("", mUserData.getNickname(), mUserData.getGooglePhotoUrl());
                }
                else
                {
                    mView.loadViewsState("", mUserData.getNickname(), null);
                }
                break;
            case Constants.LOCAL:
                mView.loadViewsState("", mUserData.getNickname(), null);
                break;
        }
    }


    @Override
    public void evaluateNavigation()
    {
        try
        {
            Intent souvenirs = new Intent(mActivity, Souvenirs.class);

            if (TextUtils.equals(UserData.getInstance(mContext).getEraName(), Constants.ERA_WORLDCUP_NAME)) //WorldCup Era
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
    public void onRetrieveTrackingSuccess(Tracking tracking)
    {
        try
        {

            mUserData.SaveUserTrackingProgess(tracking.getTotalWinCoins(), tracking.getTotalWinPrizes(), tracking.getCurrentCoinsProgress(), tracking.getTotalSouvenirs(), tracking.getAgeID());

            mUserData.saveWorldcupTracking(tracking.getCountryID(), tracking.getCountryName(), tracking.getUrlImg(), tracking.getUrlImgMarker());

            mView.updateIndicators(String.valueOf(mUserData.getTotalWonCoins()), String.valueOf(mUserData.getSavedSouvenirsCount()));

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onRetrieveTrackingError(int codeStatus, Throwable throwable, SimpleResponse errorResponse)
    {

    }
}
