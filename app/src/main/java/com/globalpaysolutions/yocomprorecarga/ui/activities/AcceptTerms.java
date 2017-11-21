package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.AcceptTermsPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.AcceptTermsView;


public class AcceptTerms extends AppCompatActivity implements AcceptTermsView
{
    private static final String TAG = AcceptTerms.class.getSimpleName();

    ImageButton btnAccept;
    //TextView tvTermsAndConditions;
    AcceptTermsPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_terms);

        presenter = new AcceptTermsPresenterImpl(this, this, this);
        presenter.checkDeviceComponents();
        presenter.setFirstTimeSettings();

        btnAccept = (ImageButton) findViewById(R.id.btnAcceptTerms);
        //tvTermsAndConditions = (TextView) findViewById(R.id.tvTermsAndConditions);

        /*tvTermsAndConditions.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.viewTerms();
            }
        });*/

    }

    public void acceptTerms(View view)
    {
        presenter.generateWebDialog();
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

    @Override
    public void displayWebDialog(String url)
    {
        try
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getString(R.string.title_terms_and_conditions));

            LayoutInflater inflater = AcceptTerms.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_terms_conditions_dialog, null);

            WebView wv = (WebView) dialogView.findViewById(R.id.wvTermsAndConditions);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.getSettings().setUseWideViewPort(true);
            wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            wv.loadUrl(url);

            wv.setWebViewClient(new WebViewClient()
            {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url)
                {
                    view.loadUrl(url);
                    return true;
                }
            });

            alert.setView(dialogView);
            alert.setNegativeButton(getString(R.string.button_accept), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int id)
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
            });
            alert.show();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
