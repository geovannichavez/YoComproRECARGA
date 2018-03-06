package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.TriviaPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.TriviaView;
import com.squareup.picasso.Picasso;

public class Trivia extends AppCompatActivity implements TriviaView
{
    //Views and layouts
    ImageView bgTrivia;
    ImageView imgSponsor;
    ImageView btnAnswer1;
    ImageView btnAnswer2;
    ImageView btnAnswer3;
    TextView lblQuestionNumber;
    TextView lblPrizeCount;
    TextView lblTimeRem;
    TextView lblQuestion;

    //MVP
    TriviaPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        bgTrivia = (ImageView) findViewById(R.id.bgTrivia);
        imgSponsor = (ImageView) findViewById(R.id.imgSponsor);
        btnAnswer1 = (ImageView) findViewById(R.id.btnAnswer1);
        btnAnswer2 = (ImageView) findViewById(R.id.btnAnswer2);
        btnAnswer3 = (ImageView) findViewById(R.id.btnAnswer3);
        lblQuestionNumber = (TextView) findViewById(R.id.lblQuestionNumber);
        lblPrizeCount = (TextView) findViewById(R.id.lblPrizeCount);
        lblTimeRem = (TextView) findViewById(R.id.lblTimeRem);
        lblQuestion = (TextView) findViewById(R.id.lblQuestion);

        mPresenter = new TriviaPresenterImpl(this, this, this);

        mPresenter.initialize();
        mPresenter.requestTrivia();
    }

    @Override
    public void initilizeViews()
    {
        //Background
        Picasso.with(this).load(R.drawable.bg_trivia).into(bgTrivia);
    }
}
