package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.LimitedFunctionalityPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.LimitedFunctionalityView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LimitedFunctionality extends AppCompatActivity implements LimitedFunctionalityView
{
    ImageButton btnWhatever;

    //MVP
    LimitedFunctionalityPresenterImpl mPresenter;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limited_functionality);

        btnWhatever = (ImageButton) findViewById(R.id.btnWhatever);

        mPresenter = new LimitedFunctionalityPresenterImpl(this, this, this);


        btnWhatever.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.navigateNext();
            }
        });

    }

    @Override
    public void navigateNextActivity()
    {
        try
        {
            Intent navigate = new Intent(this, PointsMap.class);
            navigate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(navigate);
            finish();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent navigate = new Intent(this, Main.class);
            navigate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(navigate);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
