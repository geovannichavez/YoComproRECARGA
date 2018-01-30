package com.globalpaysolutions.yocomprorecarga.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.ui.activities.NotificationDetail;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;

/**
 * Created by Josué Chávez on 27/01/2018.
 */

public class CountdownService extends Service
{
    private static final String TAG = CountdownService.class.getSimpleName();

    public static final String COUNTDOWN_BROADCAST = "com.globalpaysolutions.yocomprorecarga.services.countdown_broadcast";

    Intent mBroadcastIntent = new Intent(COUNTDOWN_BROADCAST);
    CountDownTimer mCountdownTimer = null;

    @Override
    public void onCreate()
    {
        super.onCreate();

        Log.i(TAG, "Starting timer...");

        mCountdownTimer = new CountDownTimer(Constants.PRIZE_EXCHANGE_TIME_REQUIRED, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);

                mBroadcastIntent.putExtra(Constants.INTENT_EXTRA_COUNTDOWN, millisUntilFinished);
                sendBroadcast(mBroadcastIntent);
            }

            @Override
            public void onFinish()
            {
                Log.i(TAG, "Timer finished");

                stopCountdownService();
                createCountdownFinishedNotif();

                //Resets countdown
                UserData.getInstance(getApplicationContext()).saveStartTime(System.currentTimeMillis());
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

    private void stopCountdownService()
    {
        try
        {
            stopService(new Intent(getApplicationContext(), CountdownService.class));

            Log.i(TAG, "CountdownService has been stopped.");
        }
        catch (Exception ex)
        {
            Log.e(TAG, "CountdownService could not be stopped");
        }
    }

    private void createCountdownFinishedNotif()
    {
        try
        {
            //Notification content
            String title = getApplicationContext().getResources().getString(R.string.title_notification_countdown_finished);
            String content = getApplicationContext().getResources().getString(R.string.label_notification_countdown_finished);

            //Pending intent
            Intent intent = new Intent(CountdownService.this, NotificationDetail.class);
            intent.putExtra(Constants.NOTIFICATION_TITLE_EXTRA, title);
            intent.putExtra(Constants.NOTIFICATION_BODY_EXTRA, content);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent= PendingIntent.getActivity(this,0,intent,0);

            //Notification builder
            NotificationCompat.Builder builder = new
                    NotificationCompat.Builder(this, Constants.NOTIFICATION_ID_COUNTDOWN);
            builder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.AppGreen))
                    .setAutoCancel(true)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_stat_onesignal_default)
                    .setGroup(Constants.NOTIFICATION_ID_COUNTDOWN)
                    .setContentTitle(title)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(content));

            //Notifies
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if(notificationManager != null)
                notificationManager.notify(1, builder.build());

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Notification could not be fired: " +  ex.getMessage());
        }
    }
}
