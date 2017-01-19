package com.globalpaysolutions.yocomprorecarga.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public class UserData
{
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    private int PRIVATE_MODE = 0;

    private static final String PREFERENCES_NAME = "ycrGeneralPreferences";

    private static final String KEY_COUNTRY_ID = "usr_country_id";
    private static final String KEY_COUNTRY_PHONE_CODE = "usr_country_phone_code";
    private static final String KEY_COUNTRY_IS3CODE = "usr_country_iso3code";
    private static final String KEY_COUNTRY_NAME = "usr_country_name";
    private static final String KEY_USER_PHONE = "usr_phone_number";

    private static final String KEY_HAS_ACCEPTED_TERMS = "usr_has_accepted_terms";
    private static final String KEY_HAS_SELECTED_COUNTRY = "usr_has_selected_country";
    private static final String KEY_HAS_CONFIRMED_PHONE = "usr_has_confirmed_phone";

    public UserData(Context pContext)
    {
        this.mContext = pContext;
        mPreferences = mContext.getSharedPreferences(PREFERENCES_NAME, PRIVATE_MODE);
        mEditor = mPreferences.edit();
    }

    /*
    * ********************
    *
    *       SAVE
    *
    * ******************
    */

    public void SaveUserGeneralInfo(String pCountryID, String pCountryPhoneCode, String pIso3Code, String pCountryName, String pPhone)
    {
        try
        {
            mEditor.putString(KEY_COUNTRY_ID, pCountryID);
            mEditor.putString(KEY_COUNTRY_PHONE_CODE, pCountryPhoneCode);
            mEditor.putString(KEY_COUNTRY_IS3CODE, pIso3Code);
            mEditor.putString(KEY_COUNTRY_NAME, pCountryName);
            mEditor.putString(KEY_USER_PHONE, pPhone);
            mEditor.commit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void HasAccpetedTerms(boolean pAccepted)
    {
        mEditor.putBoolean(KEY_HAS_ACCEPTED_TERMS, pAccepted);
        mEditor.commit();
    }

    public void HasSelectedCountry(boolean pSelectedCountry)
    {
        mEditor.putBoolean(KEY_HAS_SELECTED_COUNTRY, pSelectedCountry);
        mEditor.commit();
    }

    public void HasConfirmedPhone(boolean pConfirmedPhone)
    {
        mEditor.putBoolean(KEY_HAS_CONFIRMED_PHONE, pConfirmedPhone);
        mEditor.commit();
    }

    /*
    * ********************
    *
    *       SELECT
    *
    * ******************
    */

    public String GetMsisdn()
    {
        String phoneCode = mPreferences.getString(KEY_COUNTRY_PHONE_CODE, "");
        String phoneNumber = mPreferences.getString(KEY_USER_PHONE, "");

        return phoneCode + phoneNumber;

    }

    /*
    * ********************
    *
    *       DELETE
    *
    * ******************
    */
    public void DeleteUserGeneralInfo()
    {
        try
        {
            mEditor.remove(KEY_COUNTRY_ID);
            mEditor.remove(KEY_COUNTRY_PHONE_CODE);
            mEditor.remove(KEY_COUNTRY_IS3CODE);
            mEditor.remove(KEY_COUNTRY_NAME);
            mEditor.commit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
