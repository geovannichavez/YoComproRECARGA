package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.adjust.sdk.AdjustReferrerReceiver;
import com.google.android.gms.analytics.CampaignTrackingReceiver;

/**
 * Created by Josué Chávez on 25/01/2018.
 */

public class InstallReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // Adjust
        new AdjustReferrerReceiver().onReceive(context, intent);

        // Google Analytics
        new CampaignTrackingReceiver().onReceive(context, intent);
    }
}
