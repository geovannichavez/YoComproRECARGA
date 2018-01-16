package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.CombosPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.CombosView;

public class Combos extends AppCompatActivity implements CombosView
{
    private static final String TAG = Combos.class.getSimpleName();
    private CombosPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combos);

        mPresenter = new CombosPresenterImpl(this, this, this);

    }

    @Override
    public void initialViews()
    {

    }

    @Override
    public void renderCombos()
    {

    }
}
