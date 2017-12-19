package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
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
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.AcceptTermsPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.views.AcceptTermsView;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class AcceptTerms extends ImmersiveActivity implements AcceptTermsView
{
    private static final String TAG = AcceptTerms.class.getSimpleName();

    ImageButton btnAccept;
    ImageView bgWhiteTimemachine;
    AcceptTermsPresenterImpl presenter;

    ProgressBar progressBar;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_terms);

        bgWhiteTimemachine = (ImageView) findViewById(R.id.bgWhiteTimemachine);

        Picasso.with(this).load(R.drawable.bg_white_timemachine).into(bgWhiteTimemachine);

        presenter = new AcceptTermsPresenterImpl(this, this, this);
        presenter.checkDeviceComponents();
        presenter.setFirstTimeSettings();

        btnAccept = (ImageButton) findViewById(R.id.btnAcceptTerms);

    }

    public void acceptTerms(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
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

            LayoutInflater inflater = AcceptTerms.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_terms_conditions_dialog, null);

            WebView wv = (WebView) dialogView.findViewById(R.id.wvTermsAndConditions);
            progressBar = (ProgressBar) dialogView.findViewById(R.id.progressBar);

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

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon)
                {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url)
                {
                    progressBar.setVisibility(View.GONE);
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
                    accept.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
