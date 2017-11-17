package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.PrizeDetailPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.views.PrizeDetailView;

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

    //MVP
    PrizeDetailPresenterImpl mPresenter;

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
                imgPrizeType.setImageResource(R.drawable.img_prize_chest_01);
                break;
            case 2:
                imgPrizeType.setImageResource(R.drawable.img_prize_chest_02);
                break;
            case 3:
                imgPrizeType.setImageResource(R.drawable.img_prize_chest_03);
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

    public void navigateTimeMachine(View view)
    {
        Intent intent = new Intent(this, EraSelection.class);
        startActivity(intent);
    }

    public void navigateMap(View view)
    {
        Intent intent = new Intent(this, PointsMap.class);
        startActivity(intent);
    }
}
