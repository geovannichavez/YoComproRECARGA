package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IAcceptTerms;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.AcceptTermsView;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public class AcceptTermsPresenterImpl implements IAcceptTerms
{
    AcceptTermsView view;
    Context context;
    UserData userData;

    public AcceptTermsPresenterImpl(AcceptTermsView pView, AppCompatActivity pActivity, Context pContext)
    {
        this.view = pView;
        this.context = pContext;
    }

    @Override
    public void acceptTerms()
    {
        this.userData = new UserData(this.context);
        userData.HasAccpetedTerms(true);
    }
}
