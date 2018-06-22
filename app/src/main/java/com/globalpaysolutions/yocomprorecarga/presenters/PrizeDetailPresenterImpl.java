package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IPrizeDetailPresenter;
import com.globalpaysolutions.yocomprorecarga.services.CountdownService;
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
    private BroadcastReceiver mCountdownReceiver;

    public PrizeDetailPresenterImpl(Context context, AppCompatActivity activity, PrizeDetailView view)
    {
        this.mContext = context;
        this.mActivity = activity;
        this.mView = view;
        this.mUserData = UserData.getInstance(mContext);
    }

    @Override
    public void loadInitialData(Bundle extras)
    {
        try
        {
            if(extras.getBoolean(Constants.BUNDLE_PRIZE_FLAG_DETAILS, false))
            {
                mView.updateViews(extras);
            }
            else
            {
                Bundle data = new Bundle();
                data.putString(Constants.BUNDLE_PRIZE_TITLE, mUserData.getLastPrizeTitle());
                data.putString(Constants.BUNDLE_PRIZE_DESCRIPTION, mUserData.getLastPrizeDescription());
                data.putString(Constants.BUNDLE_PRIZE_CODE, mUserData.getLastPrizeCode());
                data.putString(Constants.BUNDLE_PRIZE_DIAL, mUserData.getLastPrizeDial());
                data.putString(Constants.BUNDLE_PRIZE_IMAGE, mUserData.getLastPrizeEraImage());
                data.putInt(Constants.BUNDLE_PRIZE_TYPE, mUserData.getLastPrizeLevel());
                //TODO Agregar backgrpund en datos de premio ganado
                mView.updateViews(data);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error extracting extras from bundle: " + ex.getMessage());
        }

        if(TextUtils.equals(mUserData.getAchievementFromSouvenirExchange(), Constants.ACHIEVEMENT_FROM_SOUVENIR_SALE))
            mView.showAchievementDialog();

    }

    @Override
    public void setClickListeners()
    {
        mView.setClickListeners();
    }

    @Override
    public void createSmsPrizeContent(String exchangePin)
    {
        try
        {
            /*Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + Constants.SMS_NUMBER_PRIZE_EXCHANGE));
            sendIntent.putExtra("sms_body", exchangePin);
            sendIntent.setType("vnd.android-dir/mms-sms");*/

            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", Constants.SMS_NUMBER_PRIZE_EXCHANGE);
            smsIntent.putExtra("sms_body",exchangePin);
            mView.navigateToSms(smsIntent);

        }
        catch (Exception ex)
        {
           ex.printStackTrace();
        }
    }

    @Override
    public void startCountdownService()
    {
        try
        {
            //Starts countdown service
            Intent countdownService = new Intent(mContext, CountdownService.class);
            mContext.startService(countdownService);

            //Saves start time
            UserData.getInstance(mContext).saveStartTime(System.currentTimeMillis());

            Log.i(TAG, "CountdownService started.");
        }
        catch (Exception ex)
        {
            Log.e(TAG, "CountdownService could not be started: " + ex.getMessage());
        }
    }

    @Override
    public void stopCountdownService()
    {
        try
        {
            mContext.stopService(new Intent(mContext, CountdownService.class));

            Log.i(TAG, "CountdownService has been stopped.");
        }
        catch (Exception ex)
        {
            Log.e(TAG, "CountdownService could not be stopped");
        }
    }
}
