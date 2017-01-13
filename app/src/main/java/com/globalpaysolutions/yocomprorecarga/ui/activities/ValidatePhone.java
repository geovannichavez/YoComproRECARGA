package com.globalpaysolutions.yocomprorecarga.ui.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.Country;
import com.globalpaysolutions.yocomprorecarga.presenters.IValidatePhonePresenter;
import com.globalpaysolutions.yocomprorecarga.presenters.ValidatePhonePresenterImpl;
import com.globalpaysolutions.yocomprorecarga.ui.adapters.CountriesAdapter;
import com.globalpaysolutions.yocomprorecarga.views.ValidatePhoneView;

import java.util.ArrayList;
import java.util.List;

public class ValidatePhone extends AppCompatActivity implements ValidatePhoneView
{
    //Adapters y Layouts
    EditText etPhoneNumber;
    Button btnSignin;
    RelativeLayout relSelectCountry;

    //MVP
    IValidatePhonePresenter presenter;


    //Objetos globales para activity
    List<String> countriesNames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_phone);

        etPhoneNumber = (EditText) findViewById(R.id.etConfirmPhone);
        btnSignin = (Button) findViewById(R.id.btnConfirm);
        relSelectCountry = (RelativeLayout) findViewById(R.id.relSelectCountry) ;

        presenter = new ValidatePhonePresenterImpl(this, this, this);
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

    public void Signin(View view)
    {
        Intent signin = new Intent(ValidatePhone.this, Home.class);
        startActivity(signin);
    }

    @Override
    public void renderCountries(Countries pCountries)
    {
        for(Country item : pCountries.getCountries())
        {
            countriesNames.add(item.getName());
        }
    }

    public void showCountries()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.spinner_select));

        // Initialize a new array adapter instance
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, countriesNames);

        builder.setSingleChoiceItems(arrayAdapter, -1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

        builder.setPositiveButton(getString(R.string.button_accept), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                // Just dismiss the alert dialog after selection
                // Or do something now
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
