package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.Country;
import com.globalpaysolutions.yocomprorecarga.presenters.WorldCupCountriesPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.WordlcupCountriesAdapter;
import com.globalpaysolutions.yocomprorecarga.utils.RecyclerClickListener;
import com.globalpaysolutions.yocomprorecarga.utils.RecyclerTouchListener;
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
    RecyclerView gvCountries;

    WordlcupCountriesAdapter mCountriesAdapter;
    Country mCountrySelected;


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
        gvCountries = (RecyclerView) findViewById(R.id.gvCountries);

        mPresenter = new WorldCupCountriesPresenterImpl(this, this, this);
        mPresenter.initialize();
        mPresenter.retrieveCountries();

    }

    @Override
    public void initializeViews()
    {
        btnAccept.setImageResource(R.drawable.bg_title_3);
        btnAccept.setClickable(false);
        btnAccept.setOnClickListener(selectListener);
        btnBack.setOnClickListener(backListener);
    }

    @Override
    public void renderCountries(final List<Country> countries)
    {
        try
        {
            mCountriesAdapter = new WordlcupCountriesAdapter(this, countries);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplication(), 3);

            gvCountries.setLayoutManager(layoutManager);
            gvCountries.setItemAnimator(new DefaultItemAnimator());
            gvCountries.setAdapter(mCountriesAdapter);

            gvCountries.addOnItemTouchListener(new RecyclerTouchListener(this, gvCountries, new RecyclerClickListener()
            {
                @Override
                public void onClick(View view, int position)
                {
                    mCountrySelected = countries.get(position);
                    btnAccept.setClickable(true);
                    btnAccept.setImageResource(R.drawable.btn_country_select_03);
                }

                @Override
                public void onLongClick(View view, int position)
                {

                }
            }));
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void showLoadingDialog(String message)
    {

    }

    @Override
    public void hideLoadingDialog()
    {

    }

    @Override
    public void showGenericDialog(DialogViewModel content, View.OnClickListener clickListener )
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView button = (ImageView) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(content.getTitle());
            tvDescription.setText(content.getLine1());

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            if(clickListener != null)
            {
                button.setOnClickListener(clickListener);
            }
            else
            {
                button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                    }
                });
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void navigateMap()
    {
        Intent map = new Intent(this, PointsMap.class);
        map.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(map);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            navigateBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private View.OnClickListener selectListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            mPresenter.selectCountry(mCountrySelected.getWorldCupCountryID());
        }
    };

    private View.OnClickListener backListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            navigateBack();
        }
    };

    private void navigateBack()
    {
        Intent backAction = new Intent(this, Main.class);
        backAction.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(backAction);
        finish();
    }
}

