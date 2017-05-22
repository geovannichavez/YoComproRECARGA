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
    private static final String KEY_CONSUMER_ID = "usr_consumer_id";

    private static final String KEY_HAS_ACCEPTED_TERMS = "usr_has_accepted_terms";
    private static final String KEY_HAS_SELECTED_COUNTRY = "usr_has_selected_country";
    private static final String KEY_HAS_CONFIRMED_PHONE = "usr_has_confirmed_phone";
    private static final String KEY_HAS_GRANTED_DEVICE_PERMISSIONS = "usr_has_granted_device_permissions";

    private static final String KEY_TOTAL_WON_COINS = "usr_total_won_coins";
    private static final String KEY_TOTAL_WON_PRIZES = "usr_total_won_prizes";
    private static final String KEY_CURRENT_COINS_PROGRESS = "usr_current_coins_progress";

    private static final String KEY_UNIQUE_DEVICE_ID = "app_unique_device_id";

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

    public void SaveUserGeneralInfo(String pCountryID, String pCountryPhoneCode, String pIso3Code, String pCountryName, String pPhone, int pConsumerID)
    {
        try
        {
            mEditor.putString(KEY_COUNTRY_ID, pCountryID);
            mEditor.putString(KEY_COUNTRY_PHONE_CODE, pCountryPhoneCode);
            mEditor.putString(KEY_COUNTRY_IS3CODE, pIso3Code);
            mEditor.putString(KEY_COUNTRY_NAME, pCountryName);
            mEditor.putString(KEY_USER_PHONE, pPhone);
            mEditor.putInt(KEY_CONSUMER_ID, pConsumerID);
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

    public void HasGrantedDevicePermissions(boolean pGrantedPermissions)
    {
        mEditor.putBoolean(KEY_HAS_GRANTED_DEVICE_PERMISSIONS, pGrantedPermissions);
        mEditor.commit();
    }

    public void SaveDeviceID(String pDeviceID)
    {
        mEditor.putString(KEY_UNIQUE_DEVICE_ID, pDeviceID);
        mEditor.commit();
    }

    public void SaveUserTrackingProgess(int pCoins, int pPrizes, int pCoinsProgress)
    {
        mEditor.putInt(KEY_TOTAL_WON_COINS, pCoins);
        mEditor.putInt(KEY_TOTAL_WON_PRIZES, pPrizes);
        mEditor.putInt(KEY_CURRENT_COINS_PROGRESS, pCoinsProgress);
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

    public String GetCountryID()
    {
        String countryId = mPreferences.getString(KEY_COUNTRY_ID, "0");
        return countryId;
    }

    public boolean UserAcceptedTerms()
    {
        boolean accepted = mPreferences.getBoolean(KEY_HAS_ACCEPTED_TERMS, false);
        return accepted;
    }

    public boolean UserSelectedCountry()
    {
        boolean country = mPreferences.getBoolean(KEY_HAS_SELECTED_COUNTRY, false);
        return country;
    }

    public boolean UserVerifiedPhone()
    {
        boolean phone = mPreferences.getBoolean(KEY_HAS_CONFIRMED_PHONE, false);
        return phone;
    }

    public boolean UserGrantedDevicePermissions()
    {
        return mPreferences.getBoolean(KEY_HAS_GRANTED_DEVICE_PERMISSIONS, false);
    }

    public String GetUserPhone()
    {
        String phoneCode = mPreferences.getString(KEY_USER_PHONE, "");
        return phoneCode;
    }

    public String GetUserFormattedPhone()
    {
        String phoneCode = mPreferences.getString(KEY_USER_PHONE, "");
        phoneCode = phoneCode.substring(0,4) + "-" + phoneCode.substring(4,phoneCode.length());
        return phoneCode;
    }

    public String GetIso3Code()
    {
        String iso3 = mPreferences.getString(KEY_COUNTRY_IS3CODE, "");
        return iso3;
    }

    public String GetPhoneCode()
    {
        String phoneCode = mPreferences.getString(KEY_COUNTRY_PHONE_CODE, "");
        return phoneCode;
    }

    public String GetDeviceID()
    {
        return mPreferences.getString(KEY_UNIQUE_DEVICE_ID, "");
    }

    public int GetConsumerID()
    {
        return mPreferences.getInt(KEY_CONSUMER_ID, 0);
    }

    public int GetConsumerCoins()
    {
        return mPreferences.getInt(KEY_TOTAL_WON_COINS, 0);
    }

    public int GetConsumerPrizes()
    {
        return mPreferences.getInt(KEY_TOTAL_WON_PRIZES, 0);
    }

    public int GetUserCurrentCoinsProgress()
    {
        return mPreferences.getInt(KEY_CURRENT_COINS_PROGRESS, 0);
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
