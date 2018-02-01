package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IPermissions;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.PermissionsView;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Josué Chávez on 08/03/2017.
 */

public class PermissionsPresenterImpl implements IPermissions
{
    private static final String TAG = PermissionsPresenterImpl.class.getSimpleName();

    private Context mContext;
    private AppCompatActivity mActivity;
    private UserData mUserData;
    private PermissionsView mView;
    private boolean arCompatible;

    public PermissionsPresenterImpl(PermissionsView pView, Context pContext, AppCompatActivity pActivity)
    {
        mContext = pContext;
        mView = pView;
        mActivity = pActivity;
        mUserData = UserData.getInstance(mContext);
    }

    @Override
    public void checkPermission()
    {
        try
        {
            boolean permissions;

            int FirstPermissionResult = ContextCompat.checkSelfPermission(mContext, CAMERA);
            int SecondPermissionResult = ContextCompat.checkSelfPermission(mContext, ACCESS_FINE_LOCATION);
            int ThirdPermissionResult = ContextCompat.checkSelfPermission(mContext, ACCESS_COARSE_LOCATION);
            int FourthPermissionResult = ContextCompat.checkSelfPermission(mContext, RECEIVE_SMS);
            int FifthPermissionResult = ContextCompat.checkSelfPermission(mContext, READ_SMS);
            int SixthPermissionResult = ContextCompat.checkSelfPermission(mContext, WRITE_EXTERNAL_STORAGE);

            permissions = FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                    SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                    ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                    FourthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                    FifthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                    SixthPermissionResult == PackageManager.PERMISSION_GRANTED;

            if (permissions)
            {
                mView.navegateNextActivity();
            }
            else
            {
                mUserData.HasGrantedDevicePermissions(false);
                ActivityCompat.requestPermissions(mActivity,
                        new String[]
                                {
                                    CAMERA,
                                        READ_SMS,
                                        ACCESS_FINE_LOCATION,
                                        WRITE_EXTERNAL_STORAGE
                                }, Constants.REQUEST_PERMISSION_CODE);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e(TAG, ex.getMessage());
        }

    }

    @Override
    public void onPermissionsResult(int pRequestCode, String pPermissions[], int[] pGrantResults)
    {
        switch (pRequestCode)
        {
            case Constants.REQUEST_PERMISSION_CODE:

                if (pGrantResults.length > 0)
                {
                    boolean CameraPermission = pGrantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadSMSPermission = pGrantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean AccessFineLocationPermission = pGrantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternalStorage = pGrantResults[3] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && ReadSMSPermission && AccessFineLocationPermission && writeExternalStorage)
                    {
                        mUserData.HasGrantedDevicePermissions(true);
                        mView.navegateNextActivity();
                    }
                    else
                    {
                        mView.generateToast(mContext.getString(R.string.toast_permissions_denied));
                    }
                }

                break;
        }
    }

    @Override
    public void loadBackground()
    {
        mView.loadBackground();
    }
}
