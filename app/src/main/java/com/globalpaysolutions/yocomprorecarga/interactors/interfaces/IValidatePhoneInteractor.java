package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.ValidatePhoneListener;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public interface IValidatePhoneInteractor
{
    void fethCountries(ValidatePhoneListener pListener);

    void validatePhone(ValidatePhoneListener pListener, String pMsisdn, String pCountryID);

    void saveUserGeneralInfo(String pCountryID, String pIso3Code, String pCountryName, String pPhoneCode);

    void deleteUserGeneralInfo();

}
