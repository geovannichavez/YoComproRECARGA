package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IPrizeDetailPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.PrizeDetailView;

/**
 * Created by Josué Chávez on 17/07/2017.
 */

public class PrizeDetailPresenterImpl implements IPrizeDetailPresenter
{
    private static final String TAG = PrizeDetailPresenterImpl.class.getSimpleName();

    private Context mContext;
    private AppCompatActivity mActivity;
    private PrizeDetailView mView;

    private UserData mUserData;

    public PrizeDetailPresenterImpl(Context context, AppCompatActivity activity, PrizeDetailView view)
    {
        this.mContext = context;
        this.mActivity = activity;
        this.mView = view;
        this.mUserData = new UserData(mContext);
    }

    @Override
    public void loadInitialData()
    {
        Bundle data = new Bundle();
        data.putString(Constants.BUNDLE_PRIZE_TITLE, mUserData.getLastPrizeTitle());
        data.putString(Constants.BUNDLE_PRIZE_DESCRIPTION, mUserData.getLastPrizeDescription());
        data.putString(Constants.BUNDLE_PRIZE_CODE, mUserData.getLastPrizeCode());
        data.putString(Constants.BUNDLE_PRIZE_DIAL, mUserData.getLastPrizeDial());
        data.putInt(Constants.BUNDLE_PRIZE_TYPE, mUserData.getLastPrizeLevel());
        mView.updateViews(data);
    }
}
