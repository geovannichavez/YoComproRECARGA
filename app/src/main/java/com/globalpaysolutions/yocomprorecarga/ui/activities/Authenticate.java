package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.AuthenticatePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.AuthenticateView;

public class Authenticate extends AppCompatActivity implements AuthenticateView
{
    private static final String TAG = Authenticate.class.getSimpleName();

    //MVP
    private AuthenticatePresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        mPresenter = new AuthenticatePresenterImpl(this, this, this);
    }

    @Override
    public void navigateNext()
    {

    }
}
