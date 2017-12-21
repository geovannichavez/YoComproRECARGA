package com.globalpaysolutions.yocomprorecarga.core;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.utils.OneSignalNotificationOpenedHandler;
import com.globalpaysolutions.yocomprorecarga.utils.OneSignalNotificationReceivedHandler;
import com.onesignal.OneSignal;

import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Josué Chávez on 22/03/2017.
 */

public class YoComproRecargaApplication extends Application
{
    private static YoComproRecargaApplication appSingleton;
    public static YoComproRecargaApplication getInstance()
    {
        return appSingleton;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        appSingleton = this;

        //OneSignal Required Code
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new OneSignalNotificationOpenedHandler(this))
                .setNotificationReceivedHandler(new OneSignalNotificationReceivedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init();

        //Calligraphy
        /*CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/baloo_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());*/
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/baloo_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }
}
