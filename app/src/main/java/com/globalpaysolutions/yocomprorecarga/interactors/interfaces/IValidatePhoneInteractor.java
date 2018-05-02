package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.ValidatePhoneListener;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public interface IValidatePhoneInteractor
{
    void fethCountries(ValidatePhoneListener pListener);

    void validatePhone(ValidatePhoneListener pListener, String pPhone, String pCountryID);

    void saveUserGeneralInfo(String pCountryID, String pIso3Code, String pCountryName, String pPhoneCode, String pPhone, int pConsumerID);

    void deleteUserGeneralInfo();

}
