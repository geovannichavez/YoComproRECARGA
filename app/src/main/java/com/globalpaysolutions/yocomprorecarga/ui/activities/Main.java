package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.MainPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.MainView;

public class Main extends AppCompatActivity implements MainView
{
    //Layouts and Views
ImageButton buttonSettings;

    //MVP
    MainPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSettings = (ImageButton) findViewById(R.id.buttonSettings);

        buttonSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intro = new Intent(Main.this, Intro.class);
                startActivity(intro);
            }
        });

        mPresenter = new MainPresenterImpl(this, this, this);
        mPresenter.hideStatusBar();
        mPresenter.checkUserDataCompleted();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mPresenter.hideStatusBar();
    }

    public void navigateMap(View view)
    {
        mPresenter.checkFunctionalityLimitedShown();
    }

    public void navigateAR(View view )
    {
        Intent recargo = new Intent(Main.this, CapturePrizeAR.class);
        startActivity(recargo);
    }

    public void navigateProfile(View view)
    {
        Intent profile = new Intent(Main.this, Profile.class);
        startActivity(profile);
    }

    public void navigateEraSelection(View view)
    {
        Intent eraSelection = new Intent(Main.this, EraSelection.class);
        startActivity(eraSelection);
    }

    public void navigateTopupRequest(View view)
    {
        Intent topupRequest = new Intent(Main.this, RequestTopup.class);
        startActivity(topupRequest);
    }

    public void navigateStore(View view)
    {
        if(!UserData.getInstance(this).chechUserHasSelectedEra())
        {
            Intent eraSelection = new Intent(Main.this, EraSelection.class);
            eraSelection.putExtra(Constants.BUNDLE_ERA_SELECTION_INTENT_DESTINY, Constants.BUNDLE_DESTINY_STORE);
            startActivity(eraSelection);
            finish();
        }
        else
        {
            Intent store = new Intent(Main.this, Store.class);
            startActivity(store);
        }
    }

    public void navigatePrizeRedeem(View view)
    {
        Intent prize = new Intent(Main.this, RedeemPrize.class);
        startActivity(prize);
    }

    @Override
    public void hideStatusBar()
    {
        try
        {
            View decorView = getWindow().getDecorView();
            int ui = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(ui);

            //Hide actionbar
            ActionBar actionBar = getActionBar();
            actionBar.hide();
        }
        catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override
    public void navigateStore()
    {

    }

    @Override
    public void navigateSettings()
    {

    }
}
