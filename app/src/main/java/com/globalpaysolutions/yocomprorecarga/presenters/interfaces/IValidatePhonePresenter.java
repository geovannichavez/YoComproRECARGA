package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public interface IValidatePhonePresenter
{
    void setInitialViewState();
    void fetchCountries();
    void requestToken(String pMsisdn, String pCountryID);
    void saveUserGeneralData(String pPhoneCode, String pCountryID, String pIso3Code, String pCountryName, String pPhone, int pConsumerID);
}
