package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
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
    Toolbar toolbar;
    ImageView imgPrizeType;
    EditText etPrizeCode;
    TextView lblPrizeTitle;
    TextView lblPrizeDescription;
    TextView lblExchange;

    //MVP
    PrizeDetailPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize_detail);

        toolbar = (Toolbar) findViewById(R.id.prizeDetailToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgPrizeType = (ImageView) findViewById(R.id.imgPrizeType);
        etPrizeCode = (EditText) findViewById(R.id.etPrizeCode);
        lblPrizeTitle = (TextView) findViewById(R.id.lblPrizeTitle);
        lblPrizeDescription = (TextView) findViewById(R.id.lblPrizeDescription);
        lblExchange = (TextView) findViewById(R.id.lblExchange);

        mPresenter = new PrizeDetailPresenterImpl(this, this, this);
        mPresenter.loadInitialData();

    }

    @Override
    public void updateViews(Bundle data)
    {
        lblPrizeTitle.setText(data.getString(Constants.BUNDLE_PRIZE_TITLE));
        lblPrizeDescription.setText(data.getString(Constants.BUNDLE_PRIZE_DESCRIPTION));
        lblExchange.setText(data.getString(Constants.BUNDLE_PRIZE_DIAL));
        etPrizeCode.setText(data.getString(Constants.BUNDLE_PRIZE_CODE));
        switch (data.getInt(Constants.BUNDLE_PRIZE_TYPE))
        {
            case 1:
                imgPrizeType.setImageResource(R.drawable.img_chest_sapphire);
                break;
            case 2:
                imgPrizeType.setImageResource(R.drawable.img_chest_emerald);
                break;
            case 3:
                imgPrizeType.setImageResource(R.drawable.img_chest_ruby);
                break;
            default:
                imgPrizeType.setImageResource(R.drawable.img_chest_sapphire);
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
}
