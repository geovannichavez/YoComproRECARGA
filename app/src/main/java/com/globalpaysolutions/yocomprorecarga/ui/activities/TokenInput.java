package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.ErrorResponseViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.TokenInputPresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.TokenInputView;

public class TokenInput extends AppCompatActivity implements TokenInputView
{
    //Adapters y Layouts
    private EditText etToken;
    private ImageButton btnConfirmToken;
/*    private TextView tvPortableNumber;
    private TextView tvCodeSent;*/
    private TextView tvWrongPhone;
    private ProgressDialog progressDialog;

    //MVP
    TokenInputPresenterImpl mPresenter;

    //Objetos globales
    UserData mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_input);

        etToken = (EditText) findViewById(R.id.etToken);
        btnConfirmToken = (ImageButton) findViewById(R.id.btnConfirmToken);
        /*tvPortableNumber = (TextView) findViewById(R.id.tvPortableNumber);
        tvCodeSent = (TextView) findViewById(R.id.tvCodeSent);*/
        tvWrongPhone = (TextView) findViewById(R.id.lblWrongNumber);

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
            String token = etToken.getText().toString();
            mPresenter.sendValidationToken(token);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialViewsState()
    {
        btnConfirmToken.setEnabled(false);
       /* if (Build.VERSION.SDK_INT >= 24)
        {
            tvPortableNumber.setText(Html.fromHtml(getString(R.string.label_portable_phone_number), 1));
        }
        else
        {
            tvPortableNumber.setText(Html.fromHtml(getString(R.string.label_portable_phone_number)));
        }*/
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
            Intent next = null;

            if (p3DCompatible)
            {
                next = new Intent(this, Main.class);
            }
            else
            {
                next = new Intent(this, LimitedFunctionality.class);
            }

            next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
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

            //tvPortableNumber.setText(TextUtils.concat(part1, " ", part2, " ", part3));
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

            //tvCodeSent.setText(TextUtils.concat(part1, " ", part2, " ", part3));
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
            startActivity(validatePhone);
        }
        catch (Exception ex) {  ex.printStackTrace();   }
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
