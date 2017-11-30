package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.WildcardPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.views.WildcardView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Wildcard extends AppCompatActivity implements WildcardView
{
    WildcardPresenterImpl mPresenter;

    TextView lblTitle;
    TextView lblDescription;
    ImageView imgResult;
    TextView lblResult;
    TextView lblCoinsResult;
    TextView tvDeclineTxt;
    ImageButton btnAccept;
    ImageButton btnDecline;
    ProgressDialog progressDialog;
    TextView lblAcceptChallenge;
    ImageView bg_wildcard;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wildcard);

        lblTitle = (TextView) findViewById(R.id.lblTitle);
        lblDescription = (TextView) findViewById(R.id.lblDescription);
        imgResult = (ImageView) findViewById(R.id.imgResult);
        lblResult = (TextView) findViewById(R.id.lblResult);
        lblCoinsResult = (TextView) findViewById(R.id.lblCoinsResult);
        btnAccept = (ImageButton) findViewById(R.id.btnAccept);
        btnDecline = (ImageButton) findViewById(R.id.btnDecline);
        tvDeclineTxt = (TextView) findViewById(R.id.tvDeclineTxt);
        lblAcceptChallenge = (TextView) findViewById(R.id.lblAcceptChallenge);
        bg_wildcard = (ImageView) findViewById(R.id.bg_wildcard);

        btnAccept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(Wildcard.this).animateButton(v);
                mPresenter.acceptChallenge();
            }
        });
        btnDecline.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(Wildcard.this).animateButton(v);
                Intent main = new Intent(Wildcard.this, PointsMap.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(main);
            }
        });

        mPresenter = new WildcardPresenterImpl(this, this, this);
        mPresenter.initializeViews();
    }

    @Override
    public void initialViewsState()
    {
        bg_wildcard.setImageResource(R.drawable.bg_red_recargo_theme);
        lblTitle.setText(R.string.title_initial_wildcard);
        lblDescription.setText(R.string.label_wildcard_explanation);

    }

    @Override
    public void changeWinnerView(String coins)
    {
        try
        {
            bg_wildcard.setImageResource(R.drawable.bg_dark_recargo_theme);

            lblTitle.setText(R.string.title_you_won);
            lblDescription.setText(R.string.label_you_won);
            lblCoinsResult.setText(String.format(getString(R.string.label_quantity_recarcoins), coins));
            lblResult.setText(R.string.label_win);
            btnAccept.setVisibility(View.INVISIBLE);
            lblAcceptChallenge.setVisibility(View.INVISIBLE);
            btnAccept.setEnabled(false);
            tvDeclineTxt.setText(R.string.label_back_to_map);
            imgResult.setImageResource(R.drawable.ic_wildcard_winner);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void changeLoserView(String coins)
    {
        try
        {
            bg_wildcard.setImageResource(R.drawable.bg_dark_recargo_theme);
            lblTitle.setText(R.string.title_you_lost);
            lblTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

            lblDescription.setText(R.string.label_you_lost);
            lblCoinsResult.setText(String.format(getString(R.string.label_quantity_recarcoins), coins));
            lblResult.setText(R.string.label_lost);
            lblAcceptChallenge.setVisibility(View.INVISIBLE);
            btnAccept.setVisibility(View.INVISIBLE);
            btnAccept.setEnabled(false);
            tvDeclineTxt.setText(R.string.label_back_to_map);
            imgResult.setImageResource(R.drawable.ic_wildcard_loser);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void navigateToPrizeDetail()
    {
        Intent prize = new Intent(this, PrizeDetail.class);
        prize.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(prize);
        finish();
    }

    @Override
    public void showImageDialog(DialogViewModel dialogModel, int resource)
    {
        createImageDialog(dialogModel.getTitle(), dialogModel.getLine1(), resource);
    }

    @Override
    public void showCloseableDialog(DialogViewModel dialogModel, int resource)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(Wildcard.this);
            LayoutInflater inflater = Wildcard.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic_image, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView imgSouvenir = (ImageView) dialogView.findViewById(R.id.imgDialogImage);
            ImageButton btnClose = (ImageButton) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(dialogModel.getTitle());
            tvDescription.setText(dialogModel.getLine1());
            imgSouvenir.setImageResource(resource);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            btnClose.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ButtonAnimator.getInstance(Wildcard.this).animateButton(v);
                    Intent main = new Intent(Wildcard.this, PointsMap.class);
                    main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(main);
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
    public void showLoadingDialog(String label)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(label);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void dismissLoadingDialog()
    {
        try
        {
            if (progressDialog != null && progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /*
    *
    *
    *   OTHER METHODS
    *
    * */


    public void createImageDialog(String title, String description, int resource)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(Wildcard.this);
            LayoutInflater inflater = Wildcard.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic_image, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView imgSouvenir = (ImageView) dialogView.findViewById(R.id.imgDialogImage);
            ImageButton btnClose = (ImageButton) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(title);
            tvDescription.setText(description);
            imgSouvenir.setImageResource(resource);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            btnClose.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ButtonAnimator.getInstance(Wildcard.this).animateButton(v);
                    Intent main = new Intent(Wildcard.this, PointsMap.class);
                    main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(main);
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
            Intent main = new Intent(this, PointsMap.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(main);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
