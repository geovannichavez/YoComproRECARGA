package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.Profile;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IProfilePresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Souvenirs;
import com.globalpaysolutions.yocomprorecarga.ui.activities.SouvenirsGroups;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.StringsURL;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.ProfileView;

/**
 * Created by Josué Chávez on 17/07/2017.
 */

public class ProfilePresenterImpl implements IProfilePresenter
{
    private static final String TAG = ProfilePresenterImpl.class.getSimpleName();

    private Context mContext;
    private AppCompatActivity mActivity;
    private ProfileView mView;
    private UserData mUserData;

    public ProfilePresenterImpl(Context context, AppCompatActivity activity, ProfileView view)
    {
        this.mContext = context;
        this.mActivity = activity;
        this.mView = view;
        this.mUserData = UserData.getInstance(mContext);
    }

    @Override
    public void loadBackground()
    {
        mView.setBackground();
    }

    @Override
    public void loadInitialData()
    {
        Profile profile = Profile.getCurrentProfile();
        if(profile != null)
        {
            mView.loadViewsState(profile.getName(), mUserData.getNickname(), profile.getProfilePictureUri(500, 500).toString());
        }
        else
        {
            mView.loadViewsState(mUserData.getFacebookFullname(), mUserData.getNickname(), null);
        }
    }

    @Override
    public void viewTutorial()
    {
        mView.launchChromeView(StringsURL.TUTORIAL_VIDEO_URL);
    }

    @Override
    public void evaluateNavigation()
    {
        try
        {
            Intent souvenirs = new Intent(mActivity, Souvenirs.class);

            if(TextUtils.equals(UserData.getInstance(mContext).getEraName(), "Mundial")) //WorldCup Era
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
}
