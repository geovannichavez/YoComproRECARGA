package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.presenters.PrizeDetailPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.PrizeDetailView;
import com.squareup.picasso.Picasso;

public class PrizeDetail extends ImmersiveActivity implements PrizeDetailView
{
    private static final String TAG = PrizeDetail.class.getSimpleName();

    //Views and Layouts
    TextView etPrizeCode;
    TextView lblPrizeTitle;
    TextView lblPrizeDescription;
    TextView lblExchange;
    ImageView btnSms;
    ImageView ivSponsorLogo;
    ImageView ivBackground;
    ImageView btnBack;

    //ImageButton btnBackMapPrizeDet;
    //ImageButton btnRedeemPrizeDet;

    private Constants.PrizeDetailsNavigationStack mBackstack;

    //MVP
    PrizeDetailPresenterImpl mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize_detail);

        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colo_gray_new_timemachine_2));
        Bundle extras = getIntent().getExtras();
        mBackstack = (Constants.PrizeDetailsNavigationStack) getIntent().getSerializableExtra(Constants.BUNDLE_PRIZEDET_BACKS);

        etPrizeCode = (TextView) findViewById(R.id.txtPin);
        lblPrizeTitle = (TextView) findViewById(R.id.lblPrizeTitle);
        lblPrizeDescription = (TextView) findViewById(R.id.lblPrizeDescription);
        ivSponsorLogo = (ImageView) findViewById(R.id.ivSponsorLogo);
        lblExchange = (TextView) findViewById(R.id.lblExchangeInfo);
        btnSms = (ImageView) findViewById(R.id.btnSms);
        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        btnBack = (ImageView) findViewById(R.id.btnBack);

        //btnBackMapPrizeDet = (ImageButton) findViewById(R.id.btnBackMapPrizeDet);
        //btnRedeemPrizeDet = (ImageButton) findViewById(R.id.btnStorePrizeDet);

        /*btnBackMapPrizeDet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(PrizeDetail.this).animateButton(v);
                Intent intent = new Intent(PrizeDetail.this, PointsMap.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });*/
        /*btnRedeemPrizeDet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(PrizeDetail.this).animateButton(v);
                Intent intent = new Intent(PrizeDetail.this, RedeemPrize.class);
                intent.putExtra(Constants.BUNDLE_PRE_SET_LAST_PRIZE_CODE, true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });*/

        mPresenter = new PrizeDetailPresenterImpl(this, this, this);
        mPresenter.loadInitialData(extras);
        mPresenter.setClickListeners();
        mPresenter.startCountdownService();

    }

    @Override
    public void updateViews(Bundle data)
    {
        try
        {
            Picasso.with(this).load(data.getString(Constants.BUNDLE_PRIZE_BACKGROUND)).into(ivBackground);
            Picasso.with(this).load(data.getString(Constants.BUNDLE_PRIZE_IMAGE)).into(ivSponsorLogo);
            lblPrizeTitle.setText(data.getString(Constants.BUNDLE_PRIZE_TITLE));
            lblPrizeDescription.setText(data.getString(Constants.BUNDLE_PRIZE_DESCRIPTION));
            etPrizeCode.setText(data.getString(Constants.BUNDLE_PRIZE_CODE));

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error loading views: " + ex.getMessage());
        }
    }

    @Override
    public void setClickListeners()
    {
        try
        {
            btnSms.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ButtonAnimator.getInstance(PrizeDetail.this).animateButton(v);
                    String prizePin = etPrizeCode.getText().toString();
                    mPresenter.createSmsPrizeContent(prizePin);
                }
            });

            btnBack.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    ButtonAnimator.getInstance(PrizeDetail.this).animateButton(view);
                    backNavigation();
                }
            });

        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void navigateToSms(Intent sms)
    {
        try
        {
            startActivity(sms);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showAchievementDialog()
    {
        UserData.getInstance(PrizeDetail.this).saveAchievementFromSouvenir("");

        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(PrizeDetail.this);
            LayoutInflater inflater = PrizeDetail.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_achievement_dialog, null);

            TextView lblReward = (TextView) dialogView.findViewById(R.id.lblReward);
            TextView lblAchievementName = (TextView) dialogView.findViewById(R.id.lblAchievementName);
            ImageView imgAchievement = (ImageView) dialogView.findViewById(R.id.imgAchievement);
            ImageButton btnClose = (ImageButton) dialogView.findViewById(R.id.btnClose);
            ImageButton btnAchievemtsNav = (ImageButton) dialogView.findViewById(R.id.btnAchievemtsNav);

            String coinsPrize =  String.valueOf(UserData.getInstance(this).getLastAchievement().getPrize());
            String level = String.valueOf(UserData.getInstance(this).getLastAchievement().getLevel());
            String name = String.valueOf(UserData.getInstance(this).getLastAchievement().getName());

            //Prepares resources
            int resource;
            if (UserData.getInstance(this).getLastAchievement().getLevel() == 1)
                resource = R.drawable.ic_achvs_counter_1;
            else if (UserData.getInstance(this).getLastAchievement().getLevel() == 2)
                resource = R.drawable.ic_achvs_counter_2;
            else if (UserData.getInstance(this).getLastAchievement().getLevel() == 3)
                resource = R.drawable.ic_achvs_counter_3;
            else
                resource = R.drawable.ic_achvs_counter_0;


            lblReward.setText(String.format("Tu recompensa es de %1$s RecarCoins", coinsPrize));
            lblAchievementName.setText(String.format("Has logrado el nivel %1$s  de %2$s",level, name ));
            Picasso.with(this).load(resource).into(imgAchievement);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            btnClose.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ButtonAnimator.getInstance(PrizeDetail.this).animateButton(v);
                    dialog.dismiss();
                }
            });
            btnAchievemtsNav.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ButtonAnimator.getInstance(PrizeDetail.this).animateButton(v);
                    Intent store = new Intent(PrizeDetail.this, Achievements.class);
                    store.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(store);
                    finish();
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void navigateTimeMachine(View view)
    {
        //Actually it has to navigate to 'Store'
        ButtonAnimator.getInstance(PrizeDetail.this).animateButton(view);
        Intent intent = new Intent(this, Store.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    public void navigateMap(View view)
    {

    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();

    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            backNavigation();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void backNavigation()
    {
        Intent backAction = null;

        switch (mBackstack)
        {
            case MAP:
                backAction = new Intent(PrizeDetail.this, PointsMap.class);
                break;
            case PRIZES:
                backAction = new Intent(PrizeDetail.this, PrizesHistory.class);
                break;
            case COMBOS:
                backAction = new Intent(PrizeDetail.this, Combos.class);
                break;
            case SOUVENIRS:
                backAction = new Intent(PrizeDetail.this, Souvenirs.class);
                break;
            case TRIVIA:
                backAction = new Intent(PrizeDetail.this, Trivia.class);
                break;
            default:
                backAction = new Intent(PrizeDetail.this, PrizesHistory.class);
                break;
        }

        backAction.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(backAction);
        finish();
    }
}
