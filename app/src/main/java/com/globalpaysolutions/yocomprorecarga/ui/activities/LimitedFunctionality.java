package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.LimitedFunctionalityPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.views.LimitedFunctionalityView;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LimitedFunctionality extends ImmersiveActivity implements LimitedFunctionalityView
{
    ImageButton btnWhatever;
    ImageView bgOrange;

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
        bgOrange = (ImageView) findViewById(R.id.bgOrange);

        mPresenter = new LimitedFunctionalityPresenterImpl(this, this, this);
        //mPresenter.loadBackground();

        btnWhatever.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(LimitedFunctionality.this).animateButton(v);
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
    public void loadBackground()
    {
        Picasso.with(this).load(R.drawable.bg_orange).into(bgOrange);
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
