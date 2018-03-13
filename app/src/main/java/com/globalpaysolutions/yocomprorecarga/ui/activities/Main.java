package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.MainPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.utils.ShowcaseTextPainter;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.MainView;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Main extends ImmersiveActivity implements MainView
{
    private static final String TAG = Main.class.getSimpleName();

    //Layouts and Views
    ImageButton buttonSettings;
    ImageView bgTimemachine;
    ImageView icNewChallenge;
    ImageView icNewTrivia;
    TextView tvPendingCh;
    ShowcaseView mShowcaseView;

    //MVP
    MainPresenterImpl mPresenter;

    //Global
    private int mShowcaseCounter;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSettings = (ImageButton) findViewById(R.id.buttonSettings);
        bgTimemachine = (ImageView) findViewById(R.id.bgTimemachine);
        icNewChallenge = (ImageView) findViewById(R.id.icNewChallenge);
        icNewTrivia = (ImageView) findViewById(R.id.icNewTrivia);
        tvPendingCh = (TextView) findViewById(R.id.tvPendingCh);

        mShowcaseCounter = 0;

        buttonSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(Main.this).animateButton(v);
                Intent intro = new Intent(Main.this, Intro.class);
                startActivity(intro);
            }
        });

        mPresenter = new MainPresenterImpl(this, this, this);
        mPresenter.setInitialViews();

        mPresenter.checkUserDataCompleted();

        mPresenter.checkPermissions();
        mPresenter.retrievePendings();
    }


    public void navigateMap(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
        mPresenter.checkFunctionalityLimitedShown();
    }

    public void navigateAR(View view )
    {
        Intent recargo = new Intent(Main.this, CapturePrizeAR.class);
        startActivity(recargo);
    }

    public void navigateProfile(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
        Intent profile = new Intent(Main.this, Profile.class);
        profile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(profile);
        finish();
    }

    public void navigateEraSelection(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
        Intent eraSelection = new Intent(Main.this, EraSelection.class);
        eraSelection.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(eraSelection);
        finish();
    }

    public void navigateTopupRequest(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
        Intent topupRequest = new Intent(Main.this, RequestTopup.class);
        topupRequest.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(topupRequest);
        finish();
    }

    public void navigateStore(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
        if(!UserData.getInstance(this).chechUserHasSelectedEra())
        {
            Intent eraSelection = new Intent(Main.this, EraSelection.class);
            eraSelection.putExtra(Constants.BUNDLE_ERA_SELECTION_INTENT_DESTINY, Constants.BUNDLE_DESTINY_STORE);
            startActivity(eraSelection);
            finish();
        }
        else
        {
            Intent store = new Intent(Main.this, Store.class);
            store.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(store);
            finish();
        }
    }

    /*public void navigatePrizeRedeem(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
        Intent prize = new Intent(Main.this, RedeemPrize.class);
        prize.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(prize);
        finish();
    }*/

    public void navigateLeaderboards(View view)
    {
        ButtonAnimator.getInstance(this).animateButton(view);
        Intent leaderboards = new Intent(Main.this, Leaderboards.class);
        leaderboards.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(leaderboards);
        finish();
    }

    @Override
    public void setBackground()
    {
        Picasso.with(this).load(R.drawable.bg_time_machine).priority(Picasso.Priority.HIGH).into(bgTimemachine);
    }

    @Override
    public void navigateStore()
    {

    }

    @Override
    public void navigateSettings()
    {

    }

    @Override
    public void showTutorial()
    {
        try
        {
            final Target play = new ViewTarget(findViewById(R.id.btnPlay));
            final Target profile = new ViewTarget(findViewById(R.id.btnProfile));
            final Target travel = new ViewTarget(findViewById(R.id.btnTravel));
            final Target topupRequest = new ViewTarget(findViewById(R.id.btnTopupRequest));
            final Target lightBulb = new ViewTarget(findViewById(R.id.buttonLighbub));
            final Target prize = new ViewTarget(findViewById(R.id.buttonPrize));
            final Target info = new ViewTarget(findViewById(R.id.buttonSettings));

            ShowcaseTextPainter painter = new ShowcaseTextPainter(this);

            mShowcaseView = new ShowcaseView.Builder(this)
                    .setTarget(play)
                    .blockAllTouches()
                    .setContentTitle(R.string.showcase_title_play)
                    .setContentTitlePaint(painter.createShowcaseTextPaint().get(Constants.SHOWCASE_PAINT_TITLE))
                    .setContentTextPaint(painter.createShowcaseTextPaint().get(Constants.SHOWCASE_PAINT_CONTENT))
                    .setContentText(R.string.showcase_content_play)
                    .setStyle(R.style.showcaseview_theme).setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            switch (mShowcaseCounter)
                            {
                                case 0:
                                    //Profile
                                    mShowcaseView.setShowcase(profile, true);
                                    mShowcaseView.setContentTitle(getString(R.string.showcase_title_profile));
                                    mShowcaseView.setContentText(getString(R.string.showcase_content_profile));
                                    mShowcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
                                    break;
                                case 1:
                                    //Travel
                                    mShowcaseView.setShowcase(travel, true);
                                    mShowcaseView.setContentTitle(getString(R.string.showcase_title_travel));
                                    mShowcaseView.setContentText(getString(R.string.showcase_content_travel));
                                    mShowcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                                    break;
                                case 2:
                                    //Topup Request
                                    mShowcaseView.setShowcase(topupRequest, true);
                                    mShowcaseView.setContentTitle(getString(R.string.showcase_title_topup_request));
                                    mShowcaseView.setContentText(getString(R.string.showcase_content_topup_request));
                                    mShowcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);

                                    break;
                                case 3:
                                    //Store
                                    mShowcaseView.setShowcase(lightBulb, true);
                                    mShowcaseView.setContentTitle(getString(R.string.showcase_title_store));
                                    mShowcaseView.setContentText(getString(R.string.showcase_content_store));
                                    mShowcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);

                                    break;
                                case 4:
                                    //Leaderboards
                                    RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);

                                    // This aligns button to the bottom left side of screen
                                    lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                                    lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

                                    // Set margins to the button, we add 16dp margins here
                                    int margin = ((Number) (getResources().getDisplayMetrics().density * 16)).intValue();
                                    lps.setMargins(margin, margin, margin, margin);

                                    mShowcaseView.setShowcase(prize, true);
                                    mShowcaseView.setContentTitle(getString(R.string.showcase_title_leaderboards));
                                    mShowcaseView.setContentText(getString(R.string.showcase_content_leaderboards));
                                    mShowcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                                    mShowcaseView.setButtonPosition(lps);
                                    break;
                                case 5:
                                    //Tutorial
                                    mShowcaseView.setShowcase(info, true);
                                    mShowcaseView.setContentTitle(getString(R.string.showcase_title_tutorial));
                                    mShowcaseView.setContentText(getString(R.string.showcase_content_tutorial));
                                    mShowcaseView.setButtonText(getString(R.string.button_accept));
                                    mShowcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                                    break;
                                case 6:
                                    //Dismiss
                                    mPresenter.showcaseSeen(true);
                                    mShowcaseView.hide();
                                    break;
                            }

                            mShowcaseCounter++;
                        }
                    })
                    .build();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void setPendingChallenges(String pending, boolean active)
    {
        try
        {
            if(active)
                Picasso.with(this).load(R.drawable.ic_challenge_on).into(icNewChallenge);
            else
                Picasso.with(this).load(R.drawable.ic_challenge_off).into(icNewChallenge);

            tvPendingCh.setText(pending);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error setting pending challenges: " + ex.getMessage());
        }


    }

    @Override
    public void setTriviaAvailable(boolean available)
    {
        if(available)
            Picasso.with(this).load(R.drawable.ic_trivia_on).into(icNewTrivia);
        else
            Picasso.with(this).load(R.drawable.ic_trivia_off).into(icNewTrivia);
    }

    @Override
    public void setClickListeners()
    {
        try
        {
            icNewTrivia.setOnClickListener(triviaClick);
            icNewChallenge.setOnClickListener(challengesClick);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error setting click listeners: " + ex.getMessage());
        }
    }

    private View.OnClickListener triviaClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(Main.this).animateButton(view);
            Intent store = new Intent(Main.this, Trivia.class);
            store.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(store);
            finish();
        }
    };

    private View.OnClickListener challengesClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            ButtonAnimator.getInstance(Main.this).animateButton(view);
            Intent store = new Intent(Main.this, Challenges.class);
            store.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(store);
            finish();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        mPresenter.onPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

}
