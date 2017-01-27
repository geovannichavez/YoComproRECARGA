package com.globalpaysolutions.yocomprorecarga.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by Josué Chávez on 27/01/2017.
 */

public class Validation
{
    Context mContext;

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{4}-\\d{4}";
    private static final String NAME_REGEX = "^[\\p{L} .'-]+$";
    private static final String USERNAME_REGEX = "^[_A-Za-z0-9-\\+]{3,15}$";
    private static final String AMOUNT_REGEX = "[0-9]+([,.][0-9]{1,2})?";
    private static final String VOUCHER_REGEX = "\\d+$";
    private static final String VENDOR_CODE_REGEX = "\\d{4}";

    public Validation(Context pContext)
    {
        mContext = pContext;
    }

    /*
    *
    *   PHONE NUMBER
    *
    */
    public boolean isPhoneNumber(EditText pEditText, boolean pRequired)
    {
        return IsValid(pEditText, PHONE_REGEX, null, pRequired);
    }

    public void setPhoneInutFormatter(final EditText pEditText)
    {
        //Añade formato para Telefono
        pEditText.addTextChangedListener(new TextWatcher()
        {
            int TextLength = 0;
            private static final char dash = '-';

            @Override
            public void afterTextChanged(Editable text)
            {
                String NumberText = pEditText.getText().toString();

                //Esconde el teclado después que el EditText alcanzó los 9 dígitos
                if (NumberText.length() == 9 && TextLength < NumberText.length())
                {
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
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
                String str = pEditText.getText().toString();
                TextLength = str.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
        });
    }

    /*
    *
    *   VENDOR CODE
    *
    */
    public boolean isVendorCode(EditText pEdittext, boolean pRequired)
    {
        return IsValid(pEdittext, VENDOR_CODE_REGEX, null, true);
    }



    /*
    *
    *   VALIDATORS
    *
    */
    private boolean IsValid(EditText pEditText, String pRegex, String pErrorMessage, boolean pRequired)
    {
        String text = pEditText.getText().toString().trim();

        if (pRequired && !HasText(pEditText))
            return false;

        if (pRequired && !Pattern.matches(pRegex, text))
        {
            return false;
        }

        return true;
    }

    private boolean HasText(EditText pEditText)
    {
        boolean valid = true;
        String text = pEditText.getText().toString().trim();
        pEditText.setError(null);

        if (text.length() == 0)
        {
            valid= false;
        }

        return valid;
    }

}
