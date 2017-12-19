package com.globalpaysolutions.yocomprorecarga.presenters;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.RequirementsAR;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ILimitedFunctionality;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.LimitedFunctionalityView;

import java.util.HashMap;

/**
 * Created by Josué Chávez on 30/05/2017.
 */

public class LimitedFunctionalityPresenterImpl implements ILimitedFunctionality
{
    public static final String TAG = LimitedFunctionalityPresenterImpl.class.getSimpleName();

    private Context mContext;
    private LimitedFunctionalityView mView;

    public LimitedFunctionalityPresenterImpl(Context pContext, AppCompatActivity pActivity, LimitedFunctionalityView pView)
    {
        mContext = pContext;
        mView = pView;
    }

    @Override
    public void enlistMissingComponents()
    {

        RequirementsAR result = new RequirementsAR();
        HashMap<Integer, String> componentsMap = new HashMap<>();

        final LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        final SensorManager sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);

        //Digital compass
        final boolean hasCompass = sensorManager != null && sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null;
        if (!hasCompass)
            componentsMap.put(1, mContext.getString(R.string.component_compass));

        //GPS Senson
        final boolean hasGPS = locationManager != null && locationManager.getAllProviders() != null && locationManager.getAllProviders().size() > 0;
        if (!hasGPS)
            componentsMap.put(2, mContext.getString(R.string.component_gps));

        // openGL-API version for rendering
        final boolean hasOpenGL20 = ((ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion >= 0x20000;
        if (!hasOpenGL20)
            componentsMap.put(3, mContext.getString(R.string.component_opengl));

        // Rear camera
        final boolean hasRearCam = mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
        if (!hasRearCam)
            componentsMap.put(4, mContext.getString(R.string.component_camera));

        //Checks if requierements are fulfilled
        final boolean hasAllRequirements = hasCompass && hasGPS && hasOpenGL20 && hasRearCam;
        result.setCompatible(hasAllRequirements);
        result.setComponents(componentsMap);

        Log.i(TAG, "All requirements fulfilled? " + String.valueOf(hasAllRequirements));
        Log.i(TAG, "Device features available:  Compass: " + hasCompass + "; GPS: " + hasGPS + "; OpenGL 2.0: " + hasOpenGL20 + "; Rear Cam: " + hasRearCam + ";");


        String[] components = result.getComponents().values().toArray(new String[0]);
        String componentsString = TextUtils.join(", ", components);


    }

    @Override
    public void navigateNext()
    {
        UserData.getInstance(mContext).setHasConfirmedLimitedFunctionality(true);
        mView.navigateNextActivity();
    }

    @Override
    public void loadBackground()
    {
        mView.loadBackground();
    }
}
