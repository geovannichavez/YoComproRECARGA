package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.ChallengesPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.ChallengesView;

public class Challenges extends AppCompatActivity implements ChallengesView
{

    ChallengesPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        mPresenter = new ChallengesPresenterImpl(this, this, this);
        mPresenter.retrieveChallenges();
    }

    @Override
    public void initializeViews()
    {

    }

    @Override
    public void renderChallegenes()
    {

    }

    @Override
    public void showGenericDialog(DialogViewModel dialog)
    {

    }

    @Override
    public void showLoadingDialog(String string)
    {

    }
}
