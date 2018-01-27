package com.globalpaysolutions.yocomprorecarga.services;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.utils.Constants;

/**
 * Created by Josué Chávez on 27/01/2018.
 */

public class CountdownService extends Service
{
    private static final String TAG = CountdownService.class.getSimpleName();

    public static final String COUNTDOWN_BR = "com.globalpaysolutions.yocomprorecarga.services.countdown_br";
    Intent bi = new Intent(COUNTDOWN_BR);

    CountDownTimer mCountdownTimer = null;

    @Override
    public void onCreate()
    {
        super.onCreate();

        Log.i(TAG, "Starting timer...");

        mCountdownTimer = new CountDownTimer(30000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);

                bi.putExtra(Constants.INTENT_EXTRA_COUNTDOWN, millisUntilFinished);
                sendBroadcast(bi);
            }

            @Override
            public void onFinish()
            {
                Log.i(TAG, "Timer finished");
            }
        };

        mCountdownTimer.start();
    }

    @Override
    public void onDestroy()
    {
        mCountdownTimer.cancel();
        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
