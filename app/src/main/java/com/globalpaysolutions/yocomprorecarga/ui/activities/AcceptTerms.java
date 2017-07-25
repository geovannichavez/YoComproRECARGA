package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.AcceptTermsPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.AcceptTermsView;

public class AcceptTerms extends AppCompatActivity implements AcceptTermsView
{
    private static final String TAG = AcceptTerms.class.getSimpleName();

    Button btnAccept;
    TextView tvTermsAndConditions;
    AcceptTermsPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_terms);

        presenter = new AcceptTermsPresenterImpl(this, this, this);
        presenter.checkDeviceComponents();

        btnAccept = (Button) findViewById(R.id.btnAcceptTerms);
        tvTermsAndConditions = (TextView) findViewById(R.id.tvTermsAndConditions);

        tvTermsAndConditions.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.viewTerms();
            }
        });

    }

    public void acceptTerms(View view)
    {
        presenter.acceptTerms();

        Intent accept;

        if(Build.VERSION.SDK_INT >= 23)
        {
            accept = new Intent(AcceptTerms.this, Permissions.class);
        }
        else
        {
            presenter.grantDevicePermissions();
            accept = new Intent(AcceptTerms.this, Authenticate.class);
        }

        accept.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        accept.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        accept.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        accept.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        accept.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        accept.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(accept);
    }

    @Override
    public void viewTerms(String url)
    {
        try
        {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            builder.setToolbarColor(ContextCompat.getColor(this, R.color.ActivityWhiteBackground));
            customTabsIntent.launchUrl(this, Uri.parse(url));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e(TAG, "Something went wrong launching Chrome View");
        }
    }
}
