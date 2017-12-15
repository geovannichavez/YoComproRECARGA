package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.Country;
import com.globalpaysolutions.yocomprorecarga.models.ErrorResponseViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterClientResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IValidatePhonePresenter;
import com.globalpaysolutions.yocomprorecarga.presenters.ValidatePhonePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.utils.ButtonAnimator;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.ImmersiveActivity;
import com.globalpaysolutions.yocomprorecarga.utils.Validation;
import com.globalpaysolutions.yocomprorecarga.views.ValidatePhoneView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ValidatePhone extends ImmersiveActivity implements ValidatePhoneView
{
    //Adapters y Layouts
    EditText etPhoneNumber;
    ImageButton btnSignin;
    ImageButton btnCountry;
    TextView lblSelectedCountry;

    ProgressDialog progressDialog;

    //MVP
    IValidatePhonePresenter presenter;

    //Objetos globales para activity
    List<String> countriesNames = new ArrayList<>();
    HashMap<String, Country> countriesMap = new HashMap<>();
    Country selectedCountry;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_phone);

        etPhoneNumber = (EditText) findViewById(R.id.etConfirmPhone);
        btnSignin = (ImageButton) findViewById(R.id.btnConfirm);
        btnCountry = (ImageButton) findViewById(R.id.buttonCountry);
        btnCountry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ButtonAnimator.getInstance(ValidatePhone.this).animateButton(v);
                showCountries();
            }
        });

        lblSelectedCountry = (TextView) findViewById(R.id.lblCountry);

        presenter = new ValidatePhonePresenterImpl(this, this, this);
        presenter.fetchCountries();

        if(getIntent().getBooleanExtra(Constants.BUNDLE_PHONE_RETYPE, false))
        {
            presenter.setSelectedCountry(null);
            presenter.setTypedPhone();
            btnSignin.setEnabled(true);
        }
        else
        {
            presenter.setInitialViewState();
        }


        EntriesValidations();
    }

    public void Signin(View view)
    {
        try
        {
            ButtonAnimator.getInstance(ValidatePhone.this).animateButton(view);
            String phoneNumber = etPhoneNumber.getText().toString();
            phoneNumber = phoneNumber.replace("-", "");

            this.presenter.requestToken(phoneNumber);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialViewsStates()
    {
        etPhoneNumber.setEnabled(false);
        btnSignin.setEnabled(false);
    }

    @Override
    public void retypePhoneView()
    {
        etPhoneNumber.setEnabled(true);
        btnSignin.setEnabled(false);
    }

    @Override
    public void setTypedPhone(String phone)
    {
        try
        {
            etPhoneNumber.setText(phone);
        }
        catch (Exception ex) { ex.printStackTrace();    }
    }

    @Override
    public void showLoading()
    {
        displayProgressDialog(getString(R.string.label_loading_please_wait));
    }

    @Override
    public void hideLoading()
    {
        if (progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();

        progressDialog = null;
    }

    @Override
    public void showGenericMessage(ErrorResponseViewModel pErrorMessage)
    {
        CreateDialog(pErrorMessage.getTitle(), pErrorMessage.getLine1(), pErrorMessage.getAcceptButton());
    }

    @Override
    public void renderCountries(Countries pCountries)
    {
        for(Country item : pCountries.getCountries())
        {
            countriesNames.add(item.getName());
            countriesMap.put(item.getName(), item);
        }
    }

    @Override
    public void navigateTokenInput(RegisterClientResponse pResponse)
    {
        String phone = etPhoneNumber.getText().toString();

        String rawPhone = phone.replace("-", "");
        this.presenter.saveUserGeneralData(rawPhone, pResponse.getConsumerID());

        Intent inputToken = new Intent(ValidatePhone.this, TokenInput.class);
        inputToken.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        inputToken.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        inputToken.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        inputToken.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        inputToken.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        inputToken.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        inputToken.putExtra(Constants.BUNDLE_TOKEN_VALIDATION, phone);
        startActivity(inputToken);
    }

    public void showCountries()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.label_title_select_country));
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, countriesNames);
        builder.setSingleChoiceItems(arrayAdapter, -1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String countryName = countriesNames.get(i);
                        selectedCountry = countriesMap.get(countryName);
                    }
                });

        builder.setPositiveButton(getString(R.string.button_accept), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(selectedCountry != null)
                {
                    presenter.setSelectedCountry(selectedCountry);
                    presenter.savePreselectedCountry(selectedCountry);
                }
                else
                {
                    presenter.setSelectedCountry(null);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void setSelectedCountry(Country pSelected)
    {
        lblSelectedCountry.setText(pSelected.getName());
        lblSelectedCountry.setTypeface(null, Typeface.BOLD);
        etPhoneNumber.setEnabled(true);
    }


    /*
    * ****************************************************
    *   OTROS METODOS
    * ****************************************************
    */

    private void EntriesValidations()
    {
        etPhoneNumber.addTextChangedListener(new TextWatcher()
        {

            int TextLength = 0;
            private static final char dash = '-';

            @Override
            public void afterTextChanged(Editable text)
            {

                String NumberText = etPhoneNumber.getText().toString();

                //Esconde el teclado después que el EditText alcanzó los 9 dígitos
                if (NumberText.length() == 9 && TextLength < NumberText.length())
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    btnSignin.setEnabled(true);
                }
                else
                {
                    btnSignin.setEnabled(false);
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
                String str = etPhoneNumber.getText().toString();
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
        progressDialog = new ProgressDialog(ValidatePhone.this);
        progressDialog.setMessage(pContent);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void CreateDialog(String pTitle, String pMessage, String pButton)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ValidatePhone.this);
        alertDialog.setTitle(pTitle);
        alertDialog.setMessage(pMessage);
        alertDialog.setPositiveButton(pButton, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                //etRegPass.setText("");
            }
        });
        alertDialog.show();
    }
}
