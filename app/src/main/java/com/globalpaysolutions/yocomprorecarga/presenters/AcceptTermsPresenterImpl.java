package com.globalpaysolutions.yocomprorecarga.presenters;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.RequirementsAR;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IAcceptTerms;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.StringsURL;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.AcceptTermsView;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public class AcceptTermsPresenterImpl implements IAcceptTerms
{
    private static final String TAG = ValidatePhonePresenterImpl.class.getSimpleName();
    private Context context;
    private UserData userData;
    private AcceptTermsView mView;

    public AcceptTermsPresenterImpl(AcceptTermsView pView, AppCompatActivity pActivity, Context pContext)
    {
        this.context = pContext;
        this.userData = UserData.getInstance(context);
        this.mView = pView;
    }

    @Override
    public void acceptTerms()
    {
        this.userData = UserData.getInstance(this.context);
        this.userData.HasAccpetedTerms(true);

        String uniqueID = UUID.randomUUID().toString().toUpperCase();
        Log.i(TAG, uniqueID);
        this.userData.SaveDeviceID(uniqueID);
    }

    @Override
    public void grantDevicePermissions()
    {
        this.userData = UserData.getInstance(this.context);
        this.userData.HasGrantedDevicePermissions(true);
    }

    @Override
    public void checkDeviceComponents()
    {
        RequirementsAR result = new RequirementsAR();
        HashMap<Integer, String> components = new HashMap<>();

        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        final SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        //Digital compass
        final boolean hasCompass = sensorManager != null && sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null;
        if(!hasCompass)
            components.put(1, context.getString(R.string.component_compass));

        //GPS Senson
        final boolean hasGPS = locationManager != null && locationManager.getAllProviders() != null && locationManager.getAllProviders().size() > 0;
        if(!hasGPS)
            components.put(2, context.getString(R.string.component_gps));

        // openGL-API version for rendering
        final boolean hasOpenGL20 = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion >= 0x20000;
        if(!hasOpenGL20)
            components.put(3, context.getString(R.string.component_opengl));

        // Rear camera
        final boolean hasRearCam = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
        if(!hasRearCam)
            components.put(4, context.getString(R.string.component_camera));

        //Checks if requierements are fulfilled
        final boolean hasAllRequirements = hasCompass && hasGPS && hasOpenGL20 && hasRearCam;
        result.setCompatible(hasAllRequirements);
        result.setComponents(components);

        Log.i(TAG, "All requirements fulfilled? " + String.valueOf(hasAllRequirements));
        Log.i(TAG, "Device features available:  Compass: " + hasCompass + "; GPS: " + hasGPS + "; OpenGL 2.0: " + hasOpenGL20 + "; Rear Cam: " + hasRearCam + ";");


        userData.Save3DCompatibleValue(hasAllRequirements);
    }

    @Override
    public void viewTerms()
    {
        mView.viewTerms(StringsURL.TERMS_AND_CONDITIONS_URL);
    }

    @Override
    public void generateWebDialog()
    {
        mView.displayWebDialog(StringsURL.TERMS_AND_CONDITIONS_URL);
    }
}
