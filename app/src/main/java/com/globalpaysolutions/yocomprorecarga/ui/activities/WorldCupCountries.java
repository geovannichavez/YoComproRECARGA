package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.Country;
import com.globalpaysolutions.yocomprorecarga.presenters.WorldCupCountriesPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.WorldCupCountriesView;

import java.util.List;

public class WorldCupCountries extends AppCompatActivity implements WorldCupCountriesView
{
    private static final String TAG = WorldCupCountries.class.getSimpleName();

    ImageView ivBackground;
    ImageView btnBack;
    ImageView icTutorial;
    ImageView icCountryBadge;
    ImageView btnAccept;
    TextView tvCountryName;

    private WorldCupCountriesPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_cup_countries);

        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        icTutorial = (ImageView) findViewById(R.id.icTutorial);
        icCountryBadge = (ImageView) findViewById(R.id.icCountryBadge);
        btnAccept = (ImageView) findViewById(R.id.btnAccept);
        tvCountryName = (TextView) findViewById(R.id.tvCountryName);

        mPresenter = new WorldCupCountriesPresenterImpl(this, this, this);
        mPresenter.initialize();
        mPresenter.retrieveCountries();

    }

    @Override
    public void initializeViews()
    {

    }

    @Override
    public void renderCountries(List<Country> countries)
    {

    }

    @Override
    public void showLoadingDialog(String message)
    {

    }

    @Override
    public void hideLoadingDialog()
    {

    }
}

