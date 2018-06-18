package com.globalpaysolutions.yocomprorecarga.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.globalpaysolutions.yocomprorecarga.R;

import java.util.regex.Pattern;

/**
 * Created by Josué Chávez on 27/01/2017.
 */

public class Validation
{
    Context mContext;
    CoordinatorLayout mCoordinatorLayout;

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{4}-\\d{4}";
    private static final String NAME_REGEX = "^[\\p{L} .'-]+$";
    private static final String NICKNAME_REGEX = "^[_A-Za-z0-9-\\.]{3,15}$";
    private static final String AMOUNT_REGEX = "[0-9]+([,.][0-9]{1,2})?";
    private static final String VOUCHER_REGEX = "\\d+$";
    private static final String VENDOR_CODE_REGEX = "^[0-9]{4,5}$";

    public Validation(Context pContext, CoordinatorLayout pCoordinatorLayout)
    {
        mContext = pContext;
        mCoordinatorLayout  = pCoordinatorLayout;
    }

    public Validation(Context pContext)
    {
        mContext = pContext;
    }

    /*
    *
    *   PHONE NUMBER
    *
    */
    public boolean isPhoneNumberContent(EditText pEditText, boolean pRequired)
    {
        String requiredMsg = mContext.getResources().getString(R.string.validation_required_phone_message);
        String notValidMsg = mContext.getResources().getString(R.string.validation_not_valid_phone_message);
        return IsValid(pEditText, PHONE_REGEX, requiredMsg, notValidMsg, pRequired);
    }

    public boolean isPhoneNumber(String phone)
    {
        String text = phone.trim();
        return Pattern.matches(PHONE_REGEX, text);
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
                    if(pEditText.isEnabled())
                    {
                        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    }
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
        String requiredMsg = mContext.getResources().getString(R.string.validation_required_vendor_code);
        String notValidMsg = mContext.getResources().getString(R.string.validation_not_valid_vendor_code);
        return IsValid(pEdittext, VENDOR_CODE_REGEX, requiredMsg, notValidMsg, pRequired);
    }

    /*
    *
    *
    *   NICKNAME
    *
    *
    */
    public boolean isValidNicknameSnackbar(EditText pEdittext, boolean pRequired)
    {
        String requiredMsg = mContext.getResources().getString(R.string.validation_required_nickname);
        String notValidMsg = mContext.getResources().getString(R.string.validation_not_valid_nickname);
        return IsValid(pEdittext, NICKNAME_REGEX, requiredMsg, notValidMsg, pRequired);
    }



    /*
    *
    *   VALIDATORS
    *
    */
    private boolean IsValid(EditText pEditText, String pRegex, String pRequiredMsg, String pErrorMessage, boolean pRequired)
    {
        String text = pEditText.getText().toString().trim();

        if (pRequired && !HasText(pEditText))
        {
            createSnackbar(mCoordinatorLayout, pRequiredMsg);
            return false;
        }

        if (pRequired && !Pattern.matches(pRegex, text))
        {
            createSnackbar(mCoordinatorLayout, pErrorMessage);
            return false;
        }

        return true;
    }


    public enum ValidNickname
    {
        VALID,
        REQUIRED,
        NOT_VALID
    }

    public ValidNickname checkNickname(EditText editText, boolean required)
    {
        String text = editText.getText().toString().trim();
        if (required && !HasText(editText))
        {
            return ValidNickname.REQUIRED;
        }

        if (required && !Pattern.matches(NICKNAME_REGEX, text))
        {
            return ValidNickname.NOT_VALID;
        }
        return ValidNickname.VALID;
    }

    public enum ValidateName
    {
        VALID,
        REQUIRED,
        NOT_VALID
    }

    public ValidateName checkName(EditText editText, boolean required)
    {
        String text = editText.getText().toString().trim();
        if (required && !HasText(editText))
        {
            return ValidateName.REQUIRED;
        }

        if (required && !Pattern.matches(NAME_REGEX, text))
        {
            return ValidateName.NOT_VALID;
        }
        return ValidateName.VALID;
    }

    private boolean HasText(EditText pEditText)
    {
        boolean valid = true;
        String text = pEditText.getText().toString().trim();

        if (text.length() == 0)
        {
            valid= false;
        }

        return valid;
    }


    /*
    *
    * OTROS METODOS
    *
    */

    private void createSnackbar(CoordinatorLayout pCoordinatorLayout, String pLine)
    {
        try
        {
            if(pCoordinatorLayout != null)
            {
                Snackbar mSnackbar = Snackbar.make(pCoordinatorLayout, pLine, Snackbar.LENGTH_LONG);
                View snackbarView = mSnackbar.getView();
                snackbarView.setBackgroundColor(ContextCompat.getColor(mContext,  R.color.materia_error_700));
                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                mSnackbar.show();
            }
        }
        catch (Exception ex)
        {
            Crashlytics.logException(ex);
        }


    }

}
