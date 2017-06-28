package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IAuthenticatePresenter;
import com.globalpaysolutions.yocomprorecarga.views.AuthenticateView;

/**
 * Created by Josué Chávez on 28/06/2017.
 */

public class AuthenticatePresenterImpl implements IAuthenticatePresenter
{
    private static final String TAG = AuthenticatePresenterImpl.class.getSimpleName();

    private Context mContext;
    private AuthenticateView mView;

    public AuthenticatePresenterImpl(Context pContext, AuthenticateView pView, AppCompatActivity pActivity)
    {
        this.mContext = pContext;
        this.mView = pView;
    }

    @Override
    public void validateUser()
    {
        mView.navigateNext();
    }
}
