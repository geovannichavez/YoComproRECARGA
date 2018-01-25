package com.globalpaysolutions.yocomprorecarga.core;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.LogLevel;
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
    private static final String TAG = YoComproRecargaApplication.class.getSimpleName();
    private static YoComproRecargaApplication appSingleton;
    private static final String AF_DEV_KEY = "gSWLH9Wr3aDR5pHzFQHEo4";

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

        //Adjust SDK
        String appToken = "cjt3sm3usbnk";
        String environment = AdjustConfig.ENVIRONMENT_SANDBOX;
        //String environment = AdjustConfig.ENVIRONMENT_PRODUCTION;

        AdjustConfig config = new AdjustConfig(this, appToken, environment, true);
        config.setLogLevel(LogLevel.DEBUG);

        Adjust.onCreate(config);

        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());

        //AppsFlyer
        AppsFlyerConversionListener conversionDataListener = new AppsFlyerConversionListener()
        {
            @Override
            public void onInstallConversionDataLoaded(Map<String, String> map)
            {
                Log.i(TAG, "onInstallConversionDataLoaded fired");
            }

            @Override
            public void onInstallConversionFailure(String s)
            {
                Log.i(TAG, "onInstallConversionFailure fired");
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map)
            {
                Log.i(TAG, "onAppOpenAttribution fired");
            }

            @Override
            public void onAttributionFailure(String s)
            {
                Log.i(TAG, "onAttributionFailure fired");
            }
        };

        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionDataListener, getApplicationContext());
        AppsFlyerLib.getInstance().startTracking(this);
        AppsFlyerLib.getInstance().setDebugLog(true);

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

    private static final class AdjustLifecycleCallbacks implements ActivityLifecycleCallbacks
    {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle)
        {

        }

        @Override
        public void onActivityStarted(Activity activity)
        {

        }

        @Override
        public void onActivityResumed(Activity activity)
        {
            Adjust.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity)
        {
            Adjust.onPause();
        }

        @Override
        public void onActivityStopped(Activity activity)
        {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle)
        {

        }

        @Override
        public void onActivityDestroyed(Activity activity)
        {

        }
    }
}
