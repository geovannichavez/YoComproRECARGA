package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.Country;
import com.globalpaysolutions.yocomprorecarga.models.ErrorResponseViewModel;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IValidatePhonePresenter;
import com.globalpaysolutions.yocomprorecarga.presenters.ValidatePhonePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.views.ValidatePhoneView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ValidatePhone extends AppCompatActivity implements ValidatePhoneView
{
    //Adapters y Layouts
    EditText etPhoneNumber;
    Button btnSignin;
    RelativeLayout relSelectCountry;
    ProgressDialog progressDialog;
    TextView lblSelectedCountry;
    TextView lblPhoneCode;

    //MVP
    IValidatePhonePresenter presenter;

    //Objetos globales para activity
    List<String> countriesNames = new ArrayList<>();
    HashMap<String, Country> countriesMap = new HashMap<>();
    Country selectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_phone);

        etPhoneNumber = (EditText) findViewById(R.id.etConfirmPhone);
        btnSignin = (Button) findViewById(R.id.btnConfirm);
        lblSelectedCountry = (TextView) findViewById(R.id.lblSelectedCountry);
        lblPhoneCode = (TextView) findViewById(R.id.lblPhoneCode);
        relSelectCountry = (RelativeLayout) findViewById(R.id.relSelectCountry) ;

        presenter = new ValidatePhonePresenterImpl(this, this, this);
        presenter.setInitialViewState();
        presenter.fetchCountries();

        relSelectCountry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showCountries();
            }
        });


        EntriesValidations();
    }

    public void Signin(View view)
    {
        String msisdn;
        String countryID;

        try
        {
            String phoneNumber = etPhoneNumber.getText().toString();
            phoneNumber = phoneNumber.replace("-", "");

            msisdn = selectedCountry.getPhoneCode() + phoneNumber;
            countryID = selectedCountry.getCountrycode();

            this.presenter.requestToken(msisdn, countryID);
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
    public void showErrorMessage(ErrorResponseViewModel pErrorMessage)
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
    public void navigateTokenInput()
    {
        String phone = etPhoneNumber.getText().toString();
        phone = phone.replace("-", "");
        this.presenter.saveUserGeneralData(selectedCountry.getPhoneCode(), selectedCountry.getCountrycode(), selectedCountry.getCode(), selectedCountry.getName(), phone);

        Intent inputToken = new Intent(ValidatePhone.this, TokenInput.class);
        inputToken.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        inputToken.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        inputToken.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        inputToken.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        inputToken.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        inputToken.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(inputToken);
    }

    public void showCountries()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.spinner_select));
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
                    setSelectedCountry(selectedCountry);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setSelectedCountry(Country pSelected)
    {
        lblSelectedCountry.setText(pSelected.getName());
        lblPhoneCode.setText(getString(R.string.sign_plus) +  pSelected.getPhoneCode());

        lblSelectedCountry.setTextColor(ContextCompat.getColor(this, R.color.AppGreen));
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
