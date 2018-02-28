package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.ChallengeResultData;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.Challenge;
import com.globalpaysolutions.yocomprorecarga.presenters.ChallengesPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.ChallengesAdapter;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.RecyclerClickListener;
import com.globalpaysolutions.yocomprorecarga.utils.RecyclerTouchListener;
import com.globalpaysolutions.yocomprorecarga.views.ChallengesView;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Challenges extends AppCompatActivity implements ChallengesView
{
    private static final String TAG = Challenges.class.getSimpleName();

    //Layouts and views
    ImageView bgTimemachine;
    ImageButton btnBack;
    CheckBox cbxLocation;
    ImageView btnLocation;
    private ProgressDialog mProgressDialog;

    //Global Variables
    private RecyclerView mRecyclerView;
    private ChallengesAdapter mChallengesAdapter;
    private boolean mFromMap;

    //MVP
    ChallengesPresenterImpl mPresenter;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        try
        {
            mFromMap = getIntent().getBooleanExtra(Constants.BUNDLE_CHALLENGES_BACK_MAP, false);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error retrieving value from bundle: " + ex.getMessage());
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.lvChallenges);
        bgTimemachine = (ImageView) findViewById(R.id.bgTimemachine);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        cbxLocation = (CheckBox) findViewById(R.id.cbxLocation);
        btnLocation = (ImageView) findViewById(R.id.btnLocation);

        mPresenter = new ChallengesPresenterImpl(this, this, this);
        mPresenter.initialize();
        mPresenter.retrieveChallenges();
    }

    @Override
    public void initializeViews(boolean locationVisible)
    {
        try
        {
            Picasso.with(this).load(R.drawable.bg_time_machine).into(bgTimemachine);

            //Back button click
            btnBack.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent back = null;

                    if(mFromMap)
                    {
                        back = new Intent(Challenges.this, PointsMap.class);
                        back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    }
                    else
                    {
                        back = new Intent(Challenges.this, Profile.class);
                        back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    }

                    startActivity(back);
                    finish();
                }
            });

            //Location checkbox
            cbxLocation.setChecked(locationVisible);
            cbxLocation.setClickable(false);

            //Checkbox Click Listener
            cbxLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b)
                {
                    mPresenter.locationVisible(b);
                }
            });

            //Listens for clicks on Checkbox
            btnLocation.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    cbxLocation.performClick();
                }
            });

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e(TAG, "Error at initialize: " + ex.getMessage());
        }
    }

    @Override
    public void renderChallegenes(final List<Challenge> challenges)
    {
        try
        {
            mChallengesAdapter = new ChallengesAdapter(this, challenges, mPresenter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplication());

            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            mRecyclerView.setAdapter(mChallengesAdapter);

            mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView, new RecyclerClickListener()
            {
                @Override
                public void onClick(View view, int position)
                {
                    Challenge challenge = challenges.get(position);
                    if(challenge.getStatus() == 0) //Unsolved
                    {
                        if(challenge.getCreator() == 0) //Received
                        {
                            Intent respondChallenge = new Intent(Challenges.this, PlayChallenge.class);
                            respondChallenge.putExtra(Constants.BUNDLE_CHALLENGE_ID, challenge.getChallengeID());
                            respondChallenge.putExtra(Constants.BUNDLE_CHALLENGE_QUERY, Constants.ChallengeQuery.UPDATE);
                            respondChallenge.putExtra(Constants.BUNDLE_CHALLENGE_OPPONENT_NICKNAME, challenge.getOpponentNickname());
                            respondChallenge.putExtra(Constants.BUNDLE_CHALLENGE_BET, String.valueOf(challenge.getBet()));
                            respondChallenge.putExtra(Constants.BUNDLE_CHALLENGE_SOLVED, false);
                            startActivity(respondChallenge);
                            finish();
                        }
                        else //Created
                        {
                            Intent respondChallenge = new Intent(Challenges.this, PlayChallenge.class);
                            respondChallenge.putExtra(Constants.BUNDLE_CHALLENGE_ID, challenge.getChallengeID());
                            respondChallenge.putExtra(Constants.BUNDLE_CHALLENGE_QUERY, Constants.ChallengeQuery.SELECT);
                            respondChallenge.putExtra(Constants.BUNDLE_CHALLENGE_OPPONENT_NICKNAME, challenge.getOpponentNickname());
                            respondChallenge.putExtra(Constants.BUNDLE_CHALLENGE_BET, String.valueOf(challenge.getBet()));

                            respondChallenge.putExtra(Constants.BUNDLE_CHALLENGE_USER_MOVE, challenge.getSelection());
                            respondChallenge.putExtra(Constants.BUNDLE_CHALLENGE_SOLVED, false);
                            startActivity(respondChallenge);
                            finish();
                        }
                    }
                    else //Solved
                    {
                        mPresenter.navigateChallengeResult(challenge);
                    }
                }

                @Override
                public void onLongClick(View view, int position)
                {

                }
            }));

            mChallengesAdapter.notifyDataSetChanged();

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error while trying to render challenges");
        }
    }

    @Override
    public void showGenericDialog(DialogViewModel dialog)
    {

    }

    @Override
    public void showLoadingDialog(String label)
    {
        try
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(label);
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
    public void navigateChalengeResult(ChallengeResultData challengeResult)
    {
        try
        {
            Intent result = new Intent(Challenges.this, ChallengeResult.class);
            result.putExtra(Constants.BUNDLE_CHALLENGE_RESULT_SERIALIZABLE, challengeResult);
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

            if(mFromMap)
            {
                back = new Intent(Challenges.this, PointsMap.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            }
            else
            {
                back = new Intent(Challenges.this, Profile.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            }

            startActivity(back);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
