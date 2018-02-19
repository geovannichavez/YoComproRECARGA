package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.ChallengeResultData;
import com.globalpaysolutions.yocomprorecarga.presenters.ChallengeResultPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.views.ChallengeResultView;
import com.squareup.picasso.Picasso;

public class ChallengeResult extends AppCompatActivity implements ChallengeResultView
{
    private static final String TAG = ChallengeResult.class.getSimpleName();

    //Views and layouts
    ImageView bgGrayNewMachine;
    ImageView btnBack;
    ImageView icResultTitle;
    ImageView icResultContent;
    ImageView bgMovePlayer;
    ImageView bgMoveOpponent;
    ImageView icMovePlayerIcon;
    ImageView icMoveOpponentIcon;
    ImageView btnBetSelected;
    ImageView icMovePlayer;
    ImageView icMoveOpponent;
    TextView tvTitle;
    TextView tvMovePlayer;
    TextView tvMoveOppnent;
    TextView tvTitleResult;
    TextView tvResultText;
    TextView tvSelectedBet;
    TextView tvButtonBet;

    //MVP
    ChallengeResultPresenterImpl mPresenter;

    //Global variables
    ChallengeResultData mData;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_result);

        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colo_gray_new_timemachine_2));

        bgGrayNewMachine = (ImageView) findViewById(R.id.bgGrayNewMachine);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        icResultTitle = (ImageView) findViewById(R.id.icResultTitle);
        icResultContent = (ImageView) findViewById(R.id.icResultContent);
        bgMovePlayer = (ImageView) findViewById(R.id.bgMovePlayer);
        bgMoveOpponent = (ImageView) findViewById(R.id.bgMoveOpponent);
        icMovePlayerIcon = (ImageView) findViewById(R.id.icMovePlayerIcon);
        icMoveOpponentIcon = (ImageView) findViewById(R.id.icMoveOpponentIcon);
        btnBetSelected = (ImageView) findViewById(R.id.btnBetSelected);
        icMovePlayer = (ImageView) findViewById(R.id.icMovePlayer);
        icMoveOpponent = (ImageView) findViewById(R.id.icMoveOpponent);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvMovePlayer = (TextView) findViewById(R.id.tvMovePlayer);
        tvMoveOppnent = (TextView) findViewById(R.id.tvMoveOppnent);
        tvTitleResult = (TextView) findViewById(R.id.tvTitleResult);
        tvResultText = (TextView) findViewById(R.id.tvResultText);
        tvSelectedBet = (TextView) findViewById(R.id.tvSelectedBet);
        tvButtonBet = (TextView) findViewById(R.id.tvButtonBet);

        mPresenter = new ChallengeResultPresenterImpl(this, this, this);

        try
        {
            mData = (ChallengeResultData) getIntent().getSerializableExtra(Constants.BUNDLE_CHALLENGE_RESULT_SERIALIZABLE);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error trying to get extras from intent: " + ex.getMessage());
        }

        mPresenter.initializeViews(mData);

    }

    @Override
    public void iniatializeViews(ChallengeResultData data)
    {
        try
        {
            //Background
            Picasso.with(this).load(R.drawable.bg_new_gray_machine_2).into(bgGrayNewMachine);

            tvTitleResult.setText(data.getResultTitle());
            tvResultText.setText(data.getResultContent());
            tvButtonBet.setText(String.valueOf(data.getBet()));

            Picasso.with(this).load(data.getPlayerMoveIcon()).into(icMovePlayer);
            Picasso.with(this).load(data.getOppnenteMoveIcon()).into(icMoveOpponent);

            switch (data.getOverallResult())
            {
                case 0: //Result: 0 = Lose
                    icResultTitle.setImageResource(R.drawable.ic_challenge_bad_luck);
                    bgMovePlayer.setImageResource(R.drawable.ic_challenge_result_bad);
                    bgMoveOpponent.setImageResource(R.drawable.ic_challenge_result_good);
                    icMovePlayerIcon.setImageResource(R.drawable.ic_challenge_result_1);
                    icMoveOpponentIcon.setImageResource(R.drawable.ic_challenge_result_2);
                    btnBetSelected.setImageResource(R.drawable.ic_bet_result_lose_1);
                    break;
                case 1: //Result 1 = Win
                    icResultTitle.setImageResource(R.drawable.ic_challenge_congrats);
                    bgMovePlayer.setImageResource(R.drawable.ic_challenge_result_good);
                    bgMoveOpponent.setImageResource(R.drawable.ic_challenge_result_bad);
                    icMovePlayerIcon.setImageResource(R.drawable.ic_challenge_result_2);
                    icMoveOpponentIcon.setImageResource(R.drawable.ic_challenge_result_1);
                    btnBetSelected.setImageResource(R.drawable.ic_bet_result_win_1);
                    break;
                case 2: //Result 2 = Tie
                    icResultTitle.setImageResource(R.drawable.ic_challenge_bad_luck);
                    bgMovePlayer.setImageResource(R.drawable.ic_challenge_result_bad);
                    bgMoveOpponent.setImageResource(R.drawable.ic_challenge_result_bad);
                    icMovePlayerIcon.setImageResource(R.drawable.ic_challenge_result_1);
                    icMoveOpponentIcon.setImageResource(R.drawable.ic_challenge_result_1);
                    btnBetSelected.setImageResource(R.drawable.ic_bet_result_tie_0);
                    break;
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error initializing views: " + ex.getMessage());
        }
    }

    @Override
    public void setClickListeners()
    {
        try
        {
            btnBack.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    ButtonAnimator.getInstance(ChallengeResult.this).animateButton(view);

                    Intent backAction = new Intent(ChallengeResult.this, Challenges.class);
                    backAction.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(backAction);
                    finish();
                }
            });
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
            Intent backAction = new Intent(ChallengeResult.this, Challenges.class);
            backAction.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(backAction);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
