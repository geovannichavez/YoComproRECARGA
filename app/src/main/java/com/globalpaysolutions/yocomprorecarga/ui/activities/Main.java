package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.MainPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.MainView;

public class Main extends AppCompatActivity implements MainView
{
    //Layouts and Views


    //MVP
    MainPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Intent map = new Intent(Main.this, PointsMap.class);
        startActivity(map);
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
        Intent store = new Intent(Main.this, Store.class);
        startActivity(store);
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
