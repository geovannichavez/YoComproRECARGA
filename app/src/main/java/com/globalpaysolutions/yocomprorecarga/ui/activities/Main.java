package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.MainPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.MainView;
import com.squareup.picasso.Picasso;

public class Main extends ImmersiveActivity implements MainView
{
    //Layouts and Views
    ImageButton buttonSettings;
    ImageView bgTimemachine;

    //MVP
    MainPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSettings = (ImageButton) findViewById(R.id.buttonSettings);
        bgTimemachine = (ImageView) findViewById(R.id.bgTimemachine);

        buttonSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(Main.this).animateButton(v);
                Intent intro = new Intent(Main.this, Intro.class);
                startActivity(intro);
            }
        });

        mPresenter = new MainPresenterImpl(this, this, this);
        mPresenter.setBackground();
        mPresenter.checkUserDataCompleted();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    public void navigateMap(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
        mPresenter.checkFunctionalityLimitedShown();
    }

    public void navigateAR(View view )
    {
        Intent recargo = new Intent(Main.this, CapturePrizeAR.class);
        startActivity(recargo);
    }

    public void navigateProfile(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
        Intent profile = new Intent(Main.this, Profile.class);
        profile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(profile);
        finish();
    }

    public void navigateEraSelection(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
        Intent eraSelection = new Intent(Main.this, EraSelection.class);
        eraSelection.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(eraSelection);
        finish();
    }

    public void navigateTopupRequest(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
        Intent topupRequest = new Intent(Main.this, RequestTopup.class);
        topupRequest.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(topupRequest);
        finish();
    }

    public void navigateStore(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
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
            store.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(store);
            finish();
        }
    }

    public void navigatePrizeRedeem(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
        Intent prize = new Intent(Main.this, RedeemPrize.class);
        prize.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(prize);
        finish();
    }

    @Override
    public void setBackground()
    {
        Picasso.with(this).load(R.drawable.bg_time_machine).into(bgTimemachine);
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
