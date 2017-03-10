package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IAcceptTerms;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.AcceptTermsView;

import java.util.UUID;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public class AcceptTermsPresenterImpl implements IAcceptTerms
{
    private static final String TAG = ValidatePhonePresenterImpl.class.getSimpleName();
    private AcceptTermsView view;
    private Context context;
    private UserData userData;

    public AcceptTermsPresenterImpl(AcceptTermsView pView, AppCompatActivity pActivity, Context pContext)
    {
        this.view = pView;
        this.context = pContext;
        this.userData = new UserData(context);
    }

    @Override
    public void acceptTerms()
    {
        this.userData = new UserData(this.context);
        this.userData.HasAccpetedTerms(true);

        String uniqueID = UUID.randomUUID().toString().toUpperCase();
        Log.i(TAG, uniqueID);
        this.userData.SaveDeviceID(uniqueID);
    }

    @Override
    public void grantDevicePermissions()
    {
        this.userData = new UserData(this.context);
        this.userData.HasGrantedDevicePermissions(true);
    }
}
