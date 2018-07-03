package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.ErrorResponseViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.TokenInputPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.utils.NavFlagsUtil;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.TokenInputView;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TokenInput extends ImmersiveActivity implements TokenInputView
{
    //Adapters y Layouts
    private EditText etToken;
    private ImageButton btnConfirmToken;
    private TextView tvWrongPhone;
    private ProgressDialog progressDialog;
    private ImageView bgWhiteTimemachine;

    //MVP
    TokenInputPresenterImpl mPresenter;

    //Objetos globales
    UserData mUserData;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_input);

        etToken = (EditText) findViewById(R.id.etToken);
        btnConfirmToken = (ImageButton) findViewById(R.id.btnConfirmToken);
        tvWrongPhone = (TextView) findViewById(R.id.lblWrongNumber);
        bgWhiteTimemachine = (ImageView) findViewById(R.id.bgWhiteTimemachine);

        //Get data from intent
        String userPhone = getIntent().getExtras().getString(Constants.BUNDLE_TOKEN_VALIDATION);

        //Initialize objects
        mPresenter = new TokenInputPresenterImpl(this, this, this);

        mPresenter.setInitialViewState();

        if(!TextUtils.isEmpty(userPhone))
            mPresenter.buildSentText(userPhone);
    }

    public void VerifyToken(View view)
    {
        try
        {
            ButtonAnimator.getInstance(TokenInput.this).animateButton(view);
            String token = etToken.getText().toString();

            mPresenter.validateSmsToken(token);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialViewsState()
    {
        Picasso.with(this).load(R.drawable.bg_white_timemachine).into(bgWhiteTimemachine);
        btnConfirmToken.setEnabled(false);
        EntriesValidations();
    }

    @Override
    public void setClickListeners()
    {
        //Wrong phone hyperlink
        tvWrongPhone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.retypePhoneNumber(true);
            }
        });
    }

    @Override
    public void showLoading()
    {
        displayProgressDialog(getString(R.string.label_loading_please_wait));
    }

    @Override
    public void dismissLoading()
    {
        if (progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showErrorMessage(ErrorResponseViewModel pErrorMessage)
    {
        CreateDialog(pErrorMessage.getTitle(), pErrorMessage.getLine1(), pErrorMessage.getAcceptButton());
    }

    @Override
    public void showSucceesTokenValidation()
    {
        CreateDialog(getString(R.string.dialog_success_title), getString(R.string.dialog_success_token_validation_message), getString(R.string.button_accept));
    }

    @Override
    public void navigateHome(boolean p3DCompatible)
    {
        try
        {

            Intent next = new Intent(this, Main.class);

            next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(next);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void vibrateOnSuccess()
    {
        try
        {
            Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500); //500 milisegundos
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void cleanFields()
    {
        etToken.setText("");
    }

    @Override
    public void setCallcenterContactText()
    {
        try
        {
            String phone = getString(R.string.label_callcenter_phone_number);

            SpannableString part1 = new SpannableString(getString(R.string.label_portable_phone_number_part1));
            SpannableString part2 = new SpannableString(phone);
            part2.setSpan(new StyleSpan(Typeface.BOLD), 0, phone.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            SpannableString part3 = new SpannableString(getString(R.string.label_portable_phone_number_part2));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void setCodeSentLabelText(String phoneNumber)
    {
        try
        {
            SpannableString part1 = new SpannableString(getString(R.string.label_type_token_part1));
            SpannableString part2 = new SpannableString(phoneNumber);
            part2.setSpan(new StyleSpan(Typeface.BOLD), 0, phoneNumber.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableString part3 = new SpannableString(getString(R.string.label_type_token_part2));

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void navigatePhoneValidation(boolean retypePhone)
    {
        try
        {
            Intent validatePhone = new Intent(TokenInput.this, ValidatePhone.class);
            validatePhone.putExtra(Constants.BUNDLE_PHONE_RETYPE, retypePhone);
            validatePhone.putExtra(Constants.INTENT_BUNDLE_AUTH_TYPE, UserData.getInstance(this).getAuthModeSelected());
            startActivity(validatePhone);
        }
        catch (Exception ex) {  ex.printStackTrace();   }
    }

    @Override
    public void navigateCompleteProfile()
    {
        Intent completeProfile = new Intent(TokenInput.this, CompleteProfile.class);
        NavFlagsUtil.addFlags(completeProfile);
        startActivity(completeProfile);
        finish();
    }

    /*
    * ****************************************************
    *   OTROS METODOS
    * ****************************************************
    */

    private void EntriesValidations()
    {
        etToken.addTextChangedListener(new TextWatcher()
        {
            int TextLength = 0;

            @Override
            public void afterTextChanged(Editable text)
            {
                String NumberText = etToken.getText().toString();

                //Esconde el teclado después que el EditText alcanzó los 9 dígitos
                if (NumberText.length() == 5 && TextLength < NumberText.length())
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    btnConfirmToken.setEnabled(true);
                }
                else
                {
                    btnConfirmToken.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                String str = etToken.getText().toString();
                TextLength = str.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
        });
    }

    public void displayProgressDialog(String pContent)
    {
        progressDialog = new ProgressDialog(TokenInput.this);
        progressDialog.setMessage(pContent);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void CreateDialog(String pTitle, String pMessage, String pButton)
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TokenInput.this);
        alertDialog.setTitle(pTitle);
        alertDialog.setMessage(pMessage);
        alertDialog.setPositiveButton(pButton, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
