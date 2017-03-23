package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.ui.activities.NotificationDetail;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by Josué Chávez on 22/03/2017.
 */

public class OneSignalNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler
{
    private static final String TAG = OneSignalNotificationOpenedHandler.class.getSimpleName();
    private Context mContext;

    public OneSignalNotificationOpenedHandler(Context pContext)
    {
        this.mContext = pContext;
    }

    // This fires when a notification is opened by tapping on it.
    @Override
    public void notificationOpened(OSNotificationOpenResult result)
    {
        OSNotificationAction.ActionType actionType = result.action.type;
        JSONObject data = result.notification.payload.additionalData;
        String customKey;

        if (data != null)
        {
            customKey = data.optString("customkey", null);
            if (customKey != null)
                Log.i(TAG, "customkey set with value: " + customKey);
        }

        if (actionType == OSNotificationAction.ActionType.ActionTaken)
            Log.i(TAG, "Button pressed with id: " + result.action.actionID);

        try
        {
            Intent intent = new Intent(mContext, NotificationDetail.class);
            intent.putExtra(Constants.NOTIFICATION_TITLE_EXTRA, result.notification.payload.title);
            intent.putExtra(Constants.NOTIFICATION_BODY_EXTRA, result.notification.payload.body);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }
}
