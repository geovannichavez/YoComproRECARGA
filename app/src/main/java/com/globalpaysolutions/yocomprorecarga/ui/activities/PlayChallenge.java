package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.views.PlayChallengeView;

public class PlayChallenge extends AppCompatActivity implements PlayChallengeView
{
    private static final String TAG = PlayChallenge.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_challenge);

        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.color_new_gray_background));

    }

    @Override
    public void setIncomingChallenge()
    {

    }
}
