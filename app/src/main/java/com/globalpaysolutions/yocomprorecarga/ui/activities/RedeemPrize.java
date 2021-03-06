package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.RedeemPrizeInteractorImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.views.RedeemPrizeView;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RedeemPrize extends ImmersiveActivity implements RedeemPrizeView
{

    EditText etPhone;
    EditText etPin;
    ImageButton btnActivate;
    ImageButton btnBack;
    ImageView bgTimemachine;
    ProgressDialog mProgressDialog;
    RedeemPrizeInteractorImpl mPresenter;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_prize);

        mPresenter = new RedeemPrizeInteractorImpl(this, this, this);

        etPhone = (EditText) findViewById(R.id.etPhone);
        etPin = (EditText) findViewById(R.id.etPin);

        btnActivate = (ImageButton) findViewById(R.id.btnActivate);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        bgTimemachine = (ImageView) findViewById(R.id.bgTimemachine);

        if(getIntent().getBooleanExtra(Constants.BUNDLE_PRE_SET_LAST_PRIZE_CODE, false))
        {
            mPresenter.presetPinCode();
        }

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(RedeemPrize.this).animateButton(v);
                Intent main = new Intent(RedeemPrize.this, Main.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(main);
                finish();
            }
        });

        btnActivate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(RedeemPrize.this).animateButton(v);
                String pin = etPin.getText().toString();
                String phone = etPhone.getText().toString();
                mPresenter.attemptActivatePrize(phone, pin);
            }
        });

        initializeViews();
    }

    @Override
    public void initializeViews()
    {
        Picasso.with(this).load(R.drawable.bg_time_machine).into(bgTimemachine);

        etPhone.addTextChangedListener(new TextWatcher()
        {

            int TextLength = 0;
            private static final char dash = '-';

            @Override
            public void afterTextChanged(Editable text)
            {

                String NumberText = etPhone.getText().toString();

                //Esconde el teclado después que el EditText alcanzó los 9 dígitos
                if (NumberText.length() == 9 && TextLength < NumberText.length())
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    btnActivate.setEnabled(true);
                }
                else
                {
                    btnActivate.setEnabled(false);
                }

                // Remove spacing char
                if (text.length() > 0 && (text.length() % 5) == 0)
                {
                    final char c = text.charAt(text.length() - 1);
                    if (dash == c)
                    {
                        text.delete(text.length() - 1, text.length());
                    }
                }
                // Insert char where needed.
                if (text.length() > 0 && (text.length() % 5) == 0)
                {
                    char c = text.charAt(text.length() - 1);
                    // Only if its a digit where there should be a dash we insert a dash
                    if (Character.isDigit(c) && TextUtils.split(text.toString(), String.valueOf(dash)).length <= 3)
                    {
                        text.insert(text.length() - 1, String.valueOf(dash));
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                String str = etPhone.getText().toString();
                TextLength = str.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
        });
    }

    @Override
    public void showLoadingDialog(String label)
    {
        try
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(label);
            mProgressDialog.show();
            mProgressDialog.setCancelable(false);
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
    public void createImageDialog(DialogViewModel dialogModel, int resource)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic_image, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView imgSouvenir = (ImageView) dialogView.findViewById(R.id.imgDialogImage);
            ImageButton btnClose = (ImageButton) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(dialogModel.getTitle());
            tvDescription.setText(dialogModel.getLine1());
            Picasso.with(this).load(resource).into(imgSouvenir);
            //imgSouvenir.setImageResource(resource);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            btnClose.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ButtonAnimator.getInstance(RedeemPrize.this).animateButton(v);
                    Intent main = new Intent(RedeemPrize.this, Main.class);
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
    public void createPrizeSuccessDialog(DialogViewModel dialogModel)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_prize_success, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageButton btnClose = (ImageButton) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(dialogModel.getTitle());
            tvDescription.setText(dialogModel.getLine1());

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            btnClose.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ButtonAnimator.getInstance(RedeemPrize.this).animateButton(v);
                    Intent main = new Intent(RedeemPrize.this, Main.class);
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
    public void setPrizeCode(String prizeCode)
    {
        try
        {
            etPin.setText(prizeCode);
            etPhone.requestFocus();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void showGenericDialog(String title, String message)
    {
        try
        {
            final AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(RedeemPrize.this);
            LayoutInflater inflater = RedeemPrize.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_dialog_generic, null);

            TextView tvTitle = (TextView) dialogView.findViewById(R.id.tvDialogTitle);
            TextView tvDescription = (TextView) dialogView.findViewById(R.id.tvDialogMessage);
            ImageView button = (ImageView) dialogView.findViewById(R.id.btnClose);

            tvTitle.setText(title);
            tvDescription.setText(message);

            dialog = builder.setView(dialogView).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();
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
            Intent main = new Intent(this, Main.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(main);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
