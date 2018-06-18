package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import com.globalpaysolutions.yocomprorecarga.models.Country;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public interface IValidatePhonePresenter
{
    void setInitialViewState();
    void fetchCountries();
    void requestToken(String pPhoneNumber, String authType);
    void savePreselectedCountry(Country pCountry);
    void saveUserGeneralData(String pPhone, int pConsumerID);
    void setSelectedCountry(Country pCountry);
    void setTypedPhone();
    void loadBackground();
}
