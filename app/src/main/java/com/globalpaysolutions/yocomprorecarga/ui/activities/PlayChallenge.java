package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.ChallengeResultData;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.PlayChallengePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.views.PlayChallengeView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlayChallenge extends AppCompatActivity implements PlayChallengeView
{
    private static final String TAG = PlayChallenge.class.getSimpleName();

    //Views and Layouts
    ImageView bgGrayNewMachine;
    ImageView btnBack;
    ImageView bgPaper;
    ImageView bgScissors;
    ImageView bgRock;
    ImageView btnBet;
    ImageView btnBet1;
    ImageView btnBet2;
    ImageView btnBet3;
    ImageView icRock;
    ImageView icPapper;
    ImageView icScissors;
    TextView tvNickname;
    TextView tvBet1;
    TextView tvBet2;
    TextView tvBet3;
    TextView tvBetText;
    TextView tvChooseBet;
    TextView tvChooseAttack;

    //Global Variables
    String mOpponentID;
    String mOpponentNickname;
    String mChallengeBet;
    boolean mChallengeReceived;
    int mChallengeReceivedID;

    //MVP
    PlayChallengePresenterImpl mPresenter;

    //Views and layouts
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_challenge);

        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.color_new_gray_background));

        btnBack = (ImageView) findViewById(R.id.btnBack);
        bgPaper = (ImageView) findViewById(R.id.bgPaper);
        bgScissors = (ImageView) findViewById(R.id.bgScissors);
        bgRock = (ImageView) findViewById(R.id.bgRock);
        btnBet = (ImageView) findViewById(R.id.btnBet);
        btnBet1 = (ImageView) findViewById(R.id.btnBet1);
        btnBet2 = (ImageView) findViewById(R.id.btnBet2);
        btnBet3 = (ImageView) findViewById(R.id.btnBet3);
        icRock = (ImageView) findViewById(R.id.icRock);
        icPapper = (ImageView) findViewById(R.id.icPapper);
        bgGrayNewMachine = (ImageView) findViewById(R.id.bgGrayNewMachine);
        tvNickname = (TextView) findViewById(R.id.tvNickname);
        icScissors = (ImageView) findViewById(R.id.icScissors);
        tvBet1 = (TextView) findViewById(R.id.tvBet1);
        tvBet2 = (TextView) findViewById(R.id.tvBet2);
        tvBet3 = (TextView) findViewById(R.id.tvBet3);
        tvBetText = (TextView) findViewById(R.id.tvBetText);
        tvChooseBet = (TextView) findViewById(R.id.tvChooseBet);
        tvChooseAttack = (TextView) findViewById(R.id.tvChooseAttack);

        mPresenter = new PlayChallengePresenterImpl(this, this, this);

        try
        {
            mOpponentID = getIntent().getStringExtra(Constants.BUNDLE_CHALLENGE_USER_ID);
            mChallengeReceived = getIntent().getBooleanExtra(Constants.BUNDLE_CHALLENGE_RECEIVED, false);
            mOpponentNickname = getIntent().getStringExtra(Constants.BUNDLE_CHALLENGE_OPPONENT_NICKNAME);
            mChallengeBet = getIntent().getStringExtra(Constants.BUNDLE_CHALLENGE_RECEIVED_BET);
            mChallengeReceivedID = getIntent().getIntExtra(Constants.BUNDLE_CHALLENGE_RECEIVED_ID, 0);
        }
        catch (Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }

        mPresenter.initialze();


    }

    @Override
    public void initializeViews(List<String> betsValues, String rock, String papper, String scissors)
    {
        String infoText;
        String yourMove;
        String infoBet;
        String buttonText;

        try
        {
            Picasso.with(this).load(R.drawable.bg_new_gray_machine).into(bgGrayNewMachine);
            btnBet.setEnabled(false);
            Picasso.with(this).load(R.drawable.btn_bet_off).into(btnBet);

            //Bet buttons
            tvBet1.setText(betsValues.get(0));
            tvBet2.setText(betsValues.get(1));
            tvBet3.setText(betsValues.get(2));

            //Sets icons
            Picasso.with(this).load(rock).into(icRock);
            Picasso.with(this).load(papper).into(icPapper);
            Picasso.with(this).load(scissors).into(icScissors);

            if (mChallengeReceived)
            {
                //Makes other buttons invisible
                btnBet1.setVisibility(View.INVISIBLE);
                tvBet1.setVisibility(View.INVISIBLE);
                btnBet3.setVisibility(View.INVISIBLE);
                tvBet3.setVisibility(View.INVISIBLE);
                tvBet2.setText(mChallengeBet);
                btnBet2.setImageResource(R.drawable.ic_bet_on);

                infoText = String.format(getString(R.string.title_someone_has_challenged), mOpponentNickname);
                yourMove = getString(R.string.label_challenge_choose_defense);
                infoBet = String.format(getString(R.string.label_challenge_bet_choosen_by), mOpponentNickname);
                buttonText = getString(R.string.button_challenge_defend);
            }
            else
            {
                infoText = String.format(getString(R.string.title_challenge_someone), mOpponentNickname);
                yourMove = getString(R.string.label_challenge_choose_attack);
                infoBet = getString(R.string.label_challenge_choose_bet);
                buttonText = getString(R.string.button_challenge_challenge);
            }

            //All texts
            tvNickname.setText(infoText);
            tvChooseAttack.setText(yourMove);
            tvChooseBet.setText(infoBet);
            tvBetText.setText(buttonText);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error initializing views: " + ex.getMessage());
        }

    }

    @Override
    public void setViewsListeners()
    {
        btnBack.setOnClickListener(backListener);
        bgPaper.setOnClickListener(paperListener);
        bgScissors.setOnClickListener(scissorsListener);
        bgRock.setOnClickListener(rockListener);
        btnBet1.setOnClickListener(bet1Listener);
        btnBet2.setOnClickListener(bet2Listener);
        btnBet3.setOnClickListener(bet3Listener);
        btnBet.setOnClickListener(betListener);
    }

    @Override
    public void showGenericDialog(DialogViewModel content, View.OnClickListener clickListener)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(PlayChallenge.this);
            LayoutInflater inflater = PlayChallenge.this.getLayoutInflater();
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
    public void showLoadingDialog(String text)
    {
        try
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(text);
            mProgressDialog.show();
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void hideLoadingDialog()
    {
        try
        {
            if (mProgressDialog != null && mProgressDialog.isShowing())
            {
                mProgressDialog.dismiss();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void finishActivty()
    {
        try
        {
            Intent back = null;

            if(mChallengeReceived)
            {
                back = new Intent(PlayChallenge.this, Challenges.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            }
            else
            {
                back = new Intent(PlayChallenge.this, PointsMap.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            }

            startActivity(back);
            finish();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error finishing activity: " + ex.getMessage());
        }
    }

    @Override
    public void highlightButton()
    {
        btnBet.setEnabled(true);
        btnBet.setImageResource(R.drawable.btn_bet_on);
    }

    @Override
    public void navigateResultChallenge(ChallengeResultData resultData)
    {
        try
        {
            Intent result = new Intent(PlayChallenge.this, ChallengeResult.class);
            result.putExtra(Constants.BUNDLE_CHALLENGE_RESULT_SERIALIZABLE, resultData);
            startActivity(result);
            finish();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on navigate: " + ex.getMessage());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent back = null;

            if(mChallengeReceived)
            {
                back = new Intent(PlayChallenge.this, Challenges.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            }
            else
            {
                back = new Intent(PlayChallenge.this, PointsMap.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            }

            startActivity(back);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStop()
    {
        mPresenter.clearChallenge();
        super.onStop();
    }

    /*
    *
    *
    *   LISTENERS
    *
    * */

    private View.OnClickListener backListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(PlayChallenge.this).animateButton(view);

            Intent backAction = null;

            if(mChallengeReceived)
            {
                backAction = new Intent(PlayChallenge.this, Challenges.class);
                backAction.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            }
            else
            {
                backAction = new Intent(PlayChallenge.this, PointsMap.class);
                backAction.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            }

            startActivity(backAction);
            finish();
        }
    };

    private View.OnClickListener paperListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            //Changes UI
            bgPaper.setImageResource(R.drawable.ic_attack_on);
            bgRock.setImageResource(R.drawable.ic_attack_off);
            bgScissors.setImageResource(R.drawable.ic_attack_off);

            mPresenter.chooseGameMove(Constants.CHALLENGE_PAPER_VALUE, mChallengeReceived);
        }
    };

    private View.OnClickListener scissorsListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            //Changes UI
            bgPaper.setImageResource(R.drawable.ic_attack_off);
            bgRock.setImageResource(R.drawable.ic_attack_off);
            bgScissors.setImageResource(R.drawable.ic_attack_on);

            mPresenter.chooseGameMove(Constants.CHALLENGE_SCISSORS_VALUE, mChallengeReceived);
        }
    };

    private View.OnClickListener rockListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {

            //Changes UI
            bgPaper.setImageResource(R.drawable.ic_attack_off);
            bgRock.setImageResource(R.drawable.ic_attack_on);
            bgScissors.setImageResource(R.drawable.ic_attack_off);

            mPresenter.chooseGameMove(Constants.CHALLENGE_ROCK_VALUE, mChallengeReceived);
        }
    };

    private View.OnClickListener betListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(PlayChallenge.this).animateButton(view);

            if(!mChallengeReceived)
            {
                mPresenter.createChallenge(mOpponentID);
            }
            else
            {
                mPresenter.respondChallenge(mChallengeReceivedID,  mOpponentID);
            }
        }
    };

    private View.OnClickListener bet1Listener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {

            btnBet1.setImageResource(R.drawable.ic_bet_on);
            btnBet2.setImageResource(R.drawable.ic_bet_off);
            btnBet3.setImageResource(R.drawable.ic_bet_off);
            mPresenter.choseBet(Constants.CHALLENGE_BET_VALUE_1);
        }
    };

    private View.OnClickListener bet2Listener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            btnBet1.setImageResource(R.drawable.ic_bet_off);
            btnBet2.setImageResource(R.drawable.ic_bet_on);
            btnBet3.setImageResource(R.drawable.ic_bet_off);
            mPresenter.choseBet(Constants.CHALLENGE_BET_VALUE_2);
        }
    };

    private View.OnClickListener bet3Listener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            btnBet1.setImageResource(R.drawable.ic_bet_off);
            btnBet2.setImageResource(R.drawable.ic_bet_off);
            btnBet3.setImageResource(R.drawable.ic_bet_on);
            mPresenter.choseBet(Constants.CHALLENGE_BET_VALUE_3);
        }
    };
}
