package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IMainPresenter;
import com.globalpaysolutions.yocomprorecarga.ui.activities.AcceptTerms;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Authenticate;
import com.globalpaysolutions.yocomprorecarga.ui.activities.EraSelection;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Intro;
import com.globalpaysolutions.yocomprorecarga.ui.activities.LimitedFunctionality;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Nickname;
import com.globalpaysolutions.yocomprorecarga.ui.activities.Permissions;
import com.globalpaysolutions.yocomprorecarga.ui.activities.PointsMap;
import com.globalpaysolutions.yocomprorecarga.ui.activities.TokenInput;
import com.globalpaysolutions.yocomprorecarga.ui.activities.ValidatePhone;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.MainView;

/**
 * Created by Josué Chávez on 06/11/2017.
 */

public class MainPresenterImpl implements IMainPresenter
{
    private static final String TAG = MainPresenterImpl.class.getSimpleName();

    private Context mContext;
    private MainView mView;
    private UserData mUserData;
    private AppCompatActivity mActivity;

    public MainPresenterImpl(Context context, AppCompatActivity activity, MainView view)
    {
        this.mContext = context;
        this.mView = view;
        this.mActivity = activity;
        this.mUserData = UserData.getInstance(mContext);
    }


    @Override
    public void checkUserDataCompleted()
    {
        if(!mUserData.getHasSeenIntroValue())
        {
            Intent intro = new Intent(mActivity, Intro.class);
            this.addFlags(intro);
            mContext.startActivity(intro);
        }
        else if(!mUserData.UserAcceptedTerms())
        {
            Intent acceptTerms = new Intent(mActivity, AcceptTerms.class);
            this.addFlags(acceptTerms);
            mContext.startActivity(acceptTerms);
        }
        else if(!mUserData.UserGrantedDevicePermissions())
        {
            Intent permissions = new Intent(mActivity, Permissions.class);
            this.addFlags(permissions);
            mContext.startActivity(permissions);
        }
        else if (!mUserData.isUserAuthenticated())
        {
            Intent authenticate = new Intent(mActivity, Authenticate.class);
            this.addFlags(authenticate);
            mContext.startActivity(authenticate);
        }
        else if (!mUserData.UserSelectedCountry())
        {
            Intent selectCountry = new Intent(mActivity, ValidatePhone.class);
            this.addFlags(selectCountry);
            mContext.startActivity(selectCountry);
        }
        else if(!mUserData.UserVerifiedPhone())
        {
            Intent inputToken = new Intent(mActivity, TokenInput.class);
            this.addFlags(inputToken);
            mContext.startActivity(inputToken);
        }
        else if(TextUtils.isEmpty(mUserData.getNickname()))
        {
            Intent nickname = new Intent(mActivity, Nickname.class);
            this.addFlags(nickname);
            mContext.startActivity(nickname);
        }

    }

    @Override
    public void hideStatusBar()
    {
        mView.hideStatusBar();
    }

    @Override
    public void checkFunctionalityLimitedShown()
    {
        //Checks if user has not selected era
        if(!mUserData.chechUserHasSelectedEra())
        {
            Intent eraSelection = new Intent(mActivity, EraSelection.class);
            this.addFlags(eraSelection);
            eraSelection.putExtra(Constants.BUNDLE_ERA_SELECTION_INTENT_DESTINY, Constants.BUNDLE_DESTINY_MAP);
            mContext.startActivity(eraSelection);
        }
        else
        {
            if(!mUserData.Is3DCompatibleDevice())
            {
                if(!mUserData.isUserConfirmedLimitedFunctionality())
                {
                    Intent functionality = new Intent(mActivity, LimitedFunctionality.class);
                    this.addFlags(functionality);
                    mContext.startActivity(functionality);
                }
                else
                {
                    Intent map = new Intent(mActivity, PointsMap.class);
                    this.addFlags(map);
                    mContext.startActivity(map);
                }
            }
            else
            {
                Intent map = new Intent(mActivity, PointsMap.class);
                this.addFlags(map);
                mContext.startActivity(map);
            }
        }

    }

    private void addFlags(Intent pIntent)
    {
        pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
    }
}
