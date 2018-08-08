package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.util.List;

/**
 * Created by Josué Chávez on 14/12/2017.
 */

public class MockLocationUtility
{
    private static final String TAG = MockLocationUtility.class.getSimpleName();
    private Context mContext;

    public MockLocationUtility(Context context)
    {
        this.mContext = context;
    }

    //TODO: Cambiar a 'public'
    private static boolean isMockLocation(Location location, Context context)
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            return location.isFromMockProvider();
        }
        else
        {
            String mockLocation = "0";

            try
            {
                mockLocation = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return !mockLocation.equals("0");
        }
    }

    public static boolean areThereMockPermissionApps(Context context)
    {
        int count = 0;

        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo applicationInfo : packages)
        {
            try
            {
                PackageInfo packageInfo = packageManager.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);

                // Get Permissions
                String[] requestedPermissions = packageInfo.requestedPermissions;

                if (requestedPermissions != null)
                {
                    for (String requestedPermission : requestedPermissions)
                    {
                        if (requestedPermission.equals("android.permission.ACCESS_MOCK_LOCATION") && !applicationInfo.packageName.equals(context.getPackageName()))
                        {
                            count++;
                        }
                    }
                }
            }
            catch (PackageManager.NameNotFoundException e)
            {
                Log.e("Got exception ", e.getMessage());
            }
        }

        return count > 0;
    }

    public static boolean isMockSettingsON(Context context)
    {
        String mockLocation = "0";

        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            try
            {
                mockLocation = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return !mockLocation.equals("0");
    }

    //TODO: Cambiar a 'public'
    private static int isMockAppInstalled(Context context)
    {
        PackageManager packageManager = context.getPackageManager();

        int count = 0;

        String[] blackList = new String[]
                {
                        "com.gsmartstudio.fakegps",
                        "net.marlove.mockgps",
                        "com.theappninjas.gpsjoystick",
                        "cl.datacomputer.alejandrob.newgpsjoystick",
                        "com.incorporateapps.fakegps.fre",
                        "com.lexa.fakegps",
                        "com.marlon.floating.fake.location",
                        "com.lkr.fakelocation",
                        "org.hola.gpslocation",
                        "com.blogspot.newapphorizons.fakegps",
                        "com.kfn.fakegpsfree",
                        "com.fly.gps",
                        "com.androdiki.flygpsplus",
                        "com.pokemongojoystickhack.joysticksforpokemongo",
                        "com.kfn.flygpspro",
                        "com.ninja.toolkit.pulse.fake.gps.pro"
                };

        for (String name : blackList)
        {
            try
            {
                packageManager.getPackageInfo(name, PackageManager.GET_ACTIVITIES);
                count = count + 1;
            }
            catch (PackageManager.NameNotFoundException e)
            {
                Log.i(TAG, "Not found app in blacklist");
            }
        }

        return count;
    }
}
