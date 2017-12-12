package com.globalpaysolutions.yocomprorecarga.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Josué Chávez on 22/03/2017.
 */

public class OneSignalNotificationExtender extends NotificationExtenderService
{
    final static String TAG = OneSignalNotificationExtender.class.getSimpleName();

    final static String GROUP_KEY_NOTIF = "yocomprorecarga_default_notification";

    private OSNotificationReceivedResult mNotification;

    @Override
    protected boolean onNotificationProcessing(final OSNotificationReceivedResult notification)
    {
        mNotification = notification;

        OverrideSettings overrideSettings = new OverrideSettings();

        overrideSettings.extender = new NotificationCompat.Extender()
        {
            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder)
            {
                builder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.AppGreen))
                        .setAutoCancel(true)
                        .setGroupSummary(true)
                        .setGroup(GROUP_KEY_NOTIF)
                        .setContentText(notification.payload.body);

                if(!TextUtils.isEmpty(mNotification.payload.bigPicture))
                {
                    Bitmap image = getBitmapfromUrl(mNotification.payload.bigPicture);

                    if(image != null)
                    {
                        final NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
                        bigPictureStyle.bigPicture(image);
                        bigPictureStyle.setSummaryText(mNotification.payload.body);
                        builder.setStyle(bigPictureStyle);
                    }
                    else
                    {
                        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(mNotification.payload.body));
                    }
                }
                else
                {
                    builder.setStyle(new NotificationCompat.BigTextStyle().bigText(mNotification.payload.body));
                }

                return builder;
            }
        };

        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
        Log.d(TAG, "Notification displayed with id: " + displayedResult.androidNotificationId);

        return true;
    }

    /*
    * To get a Bitmap image from the URL received
    * */
    public Bitmap getBitmapfromUrl(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
