package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.TriviaPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.TriviaView;
import com.squareup.picasso.Picasso;

public class Trivia extends AppCompatActivity implements TriviaView
{
    //Views and layouts
    ImageView bgTrivia;

    //MVP
    TriviaPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        bgTrivia = (ImageView) findViewById(R.id.bgTrivia);

        mPresenter = new TriviaPresenterImpl(this, this, this);


        mPresenter.initialize();
    }

    @Override
    public void initilizeViews()
    {
        //Background
        Picasso.with(this).load(R.drawable.bg_trivia).into(bgTrivia);
    }
}
