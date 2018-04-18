package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.ProfilePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.views.ProfileView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Profile extends ImmersiveActivity implements ProfileView
{
    private static final String TAG = Profile.class.getSimpleName();

    //Views and layouts
    ImageView btnBack;
    CircleImageView imgProfilePic;
    ImageView bgTimemachine;
    ImageView icCountry;
    ImageView icPanelPrizes;
    ImageView icPanelAchvs;
    ImageView icPanelSouvs;
    ImageView icPanelChallenges;
    TextView tvNickname;
    TextView tvCoins;
    TextView tvSouvs;

    //MVP
    ProfilePresenterImpl mPresenter;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        bgTimemachine = (ImageView) findViewById(R.id.bgTimemachine);
        imgProfilePic = (CircleImageView) findViewById(R.id.imgProfilePic);
        icPanelPrizes = (ImageView) findViewById(R.id.icPanelPrizes);
        icPanelAchvs = (ImageView) findViewById(R.id.icPanelAchvs);
        icPanelSouvs = (ImageView) findViewById(R.id.icPanelSouvs);
        icCountry = (ImageView) findViewById(R.id.icCountry);
        icPanelChallenges = (ImageView) findViewById(R.id.icPanelChallenges);
        tvNickname = (TextView) findViewById(R.id.tvNickname);
        tvCoins = (TextView) findViewById(R.id.tvCoins);
        tvSouvs = (TextView) findViewById(R.id.tvSouvs);



        mPresenter = new ProfilePresenterImpl(this, this, this);
        mPresenter.retrieveTracking();
        mPresenter.loadInitialData();
    }

    @Override
    public void loadViewsState(String countryUrl, String nickname, String photoUrl)
    {
        try
        {
            Picasso.with(this).load(R.drawable.bg_background_4).into(bgTimemachine);
            Picasso.with(this).load(photoUrl).into(imgProfilePic);
            tvNickname.setText(nickname);

            btnBack.setOnClickListener(backListener);
            icPanelSouvs.setOnClickListener(souvsListener);
            icPanelAchvs.setOnClickListener(achievesListener);
            icPanelPrizes.setOnClickListener(prizesListener);
            icPanelChallenges.setOnClickListener(challengesListener);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void generateToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showGenericDialog(DialogViewModel model)
    {

    }

    @Override
    public void updateIndicators(String totalCoins, String totalSouvenirs)
    {
        tvCoins.setText(totalCoins);
        tvSouvs.setText(totalSouvenirs);
    }

    @Override
    public void navigateSouvenirs(Intent souvenirs)
    {
        try
        {
            souvenirs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(souvenirs);
            finish();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error navigating: " + ex.getMessage());
        }
    }

    @Override
    public void loadCountryBadge(String worldcupCountryUrl)
    {
        Picasso.with(this).load(worldcupCountryUrl).into(icCountry);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            navigateBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void navigateBack()
    {
        try
        {
            Intent main = new Intent(Profile.this, Main.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(main);
            finish();
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    private View.OnClickListener souvsListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(Profile.this).animateButton(view);
            mPresenter.evaluateNavigation();
        }
    };
    private View.OnClickListener achievesListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(Profile.this).animateButton(view);
            Intent achievements = new Intent(Profile.this, Achievements.class);
            achievements.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(achievements);
            finish();
        }
    };
    private View.OnClickListener prizesListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(Profile.this).animateButton(view);
            Intent prizes = new Intent(Profile.this, PrizesHistory.class);
            prizes.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(prizes);
            finish();
        }
    };
    private View.OnClickListener challengesListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(Profile.this).animateButton(view);
            Intent challenges = new Intent(Profile.this, Challenges.class);
            challenges.putExtra(Constants.BUNDLE_CHALLENGES_BACK_MAP, Constants.ChallengesBackStack.PROFILE);
            challenges.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(challenges);
            finish();
        }
    };

    private View.OnClickListener backListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(Profile.this).animateButton(view);
            navigateBack();
        }
    };


}
