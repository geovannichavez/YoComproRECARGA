package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.LimitedFunctionalityPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.LimitedFunctionalityView;

public class LimitedFunctionality extends AppCompatActivity implements LimitedFunctionalityView
{
    ImageButton btnWhatever;

    //MVP
    LimitedFunctionalityPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limited_functionality);

        btnWhatever = (ImageButton) findViewById(R.id.btnWhatever);

        mPresenter = new LimitedFunctionalityPresenterImpl(this, this, this);

        //mPresenter.enlistMissingComponents();

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
