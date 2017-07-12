package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.PermissionsPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.PermissionsView;

public class Permissions extends AppCompatActivity implements PermissionsView
{
    //Views and layouts
    Button btnPermissions;

    //MVP
    PermissionsPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        btnPermissions = (Button) findViewById(R.id.btnPermissions);

        mPresenter = new PermissionsPresenterImpl(this, this, this);
    }

    public void RequestPermissions(View view)
    {
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
