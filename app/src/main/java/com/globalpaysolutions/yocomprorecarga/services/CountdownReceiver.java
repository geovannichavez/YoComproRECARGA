package com.globalpaysolutions.yocomprorecarga.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.utils.Constants;

/**
 * Created by Josué Chávez on 27/01/2018.
 */

public class CountdownReceiver extends BroadcastReceiver
{
    private static final String TAG = CountdownReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        //updateGUI(intent); // or whatever method used to update your GUI fields
    }

    private long getRemainingMillis(Intent intent)
    {
        long millisUntilFinished = 0;

        try
        {
            if (intent.getExtras() != null)
            {
                millisUntilFinished = intent.getLongExtra(Constants.INTENT_EXTRA_COUNTDOWN, 0);

                Log.i(TAG, "Countdown seconds remaining: " +  millisUntilFinished / 1000);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Remaining millis could not be returned as string: " + ex.getMessage());
        }

        return millisUntilFinished;
    }
}
