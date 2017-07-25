package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.facebook.Profile;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IProfilePresenter;
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
}
