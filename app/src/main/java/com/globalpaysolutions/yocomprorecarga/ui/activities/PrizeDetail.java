package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.PrizeDetailPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.PrizeDetailView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PrizeDetail extends AppCompatActivity implements PrizeDetailView
{
    private static final String TAG = PrizeDetail.class.getSimpleName();

    //Views and Layouts
    ImageView imgPrizeType;
    TextView etPrizeCode;
    TextView lblPrizeTitle;
    TextView lblPrizeDescription;
    TextView lblExchange;
    ImageButton btnSms;

    ImageButton btnBackMapPrizeDet;
    ImageButton btnStorePrizeDet;

    //MVP
    PrizeDetailPresenterImpl mPresenter;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize_detail);


        imgPrizeType = (ImageView) findViewById(R.id.imgChestPrize);
        etPrizeCode = (TextView) findViewById(R.id.txtPin);
        lblPrizeTitle = (TextView) findViewById(R.id.lblPrizeTitle);
        lblPrizeDescription = (TextView) findViewById(R.id.lblPrizeDescription);
        lblExchange = (TextView) findViewById(R.id.lblExchangeInfo);
        btnSms = (ImageButton) findViewById(R.id.btnSms);

        btnBackMapPrizeDet = (ImageButton) findViewById(R.id.btnBackMapPrizeDet);
        btnStorePrizeDet = (ImageButton) findViewById(R.id.btnStorePrizeDet);

        btnBackMapPrizeDet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(PrizeDetail.this).animateButton(v);
                Intent intent = new Intent(PrizeDetail.this, PointsMap.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        btnStorePrizeDet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(PrizeDetail.this).animateButton(v);
                Intent intent = new Intent(PrizeDetail.this, Store.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        mPresenter = new PrizeDetailPresenterImpl(this, this, this);
        mPresenter.loadInitialData();
        mPresenter.setClickListeners();

    }

    @Override
    public void updateViews(Bundle data)
    {
        lblPrizeTitle.setText(data.getString(Constants.BUNDLE_PRIZE_TITLE));
        lblPrizeDescription.setText(data.getString(Constants.BUNDLE_PRIZE_DESCRIPTION));
        etPrizeCode.setText(data.getString(Constants.BUNDLE_PRIZE_CODE));

        switch (data.getInt(Constants.BUNDLE_PRIZE_TYPE))
        {
            case 1:
                imgPrizeType.setImageResource(R.drawable.img_prize_chest_03);
                break;
            case 2:
                imgPrizeType.setImageResource(R.drawable.img_prize_chest_02);
                break;
            case 3:
                imgPrizeType.setImageResource(R.drawable.img_prize_chest_01);
                break;
            default:
                imgPrizeType.setImageResource(R.drawable.img_prize_chest_03);
                break;
        }

    }

    @Override
    public void showGenericToast(String message)
    {

    }

    @Override
    public void showGenericDialog(DialogViewModel dialogViewModel)
    {

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
            imgAchievement.setImageResource(resource);

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
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(this, PointsMap.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
