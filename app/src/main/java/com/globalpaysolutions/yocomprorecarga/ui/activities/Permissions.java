package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.PermissionsPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.views.PermissionsView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Permissions extends ImmersiveActivity implements PermissionsView
{
    //Views and layouts
    ImageButton btnPermissions;

    //MVP
    PermissionsPresenterImpl mPresenter;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        btnPermissions = (ImageButton) findViewById(R.id.btnPermissions);

        mPresenter = new PermissionsPresenterImpl(this, this, this);
    }

    public void RequestPermissions(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
        mPresenter.checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        mPresenter.onPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void navegateNextActivity()
    {
        try
        {
            Intent authenticate = new Intent(this, Authenticate.class);
            authenticate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            authenticate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            authenticate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            authenticate.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            authenticate.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            authenticate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(authenticate);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void generateToast(String pContent)
    {
        Toast.makeText(Permissions.this, pContent, Toast.LENGTH_LONG).show();
    }
}
