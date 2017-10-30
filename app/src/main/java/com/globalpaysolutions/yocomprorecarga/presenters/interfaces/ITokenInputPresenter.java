package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import com.globalpaysolutions.yocomprorecarga.models.Country;

/**
 * Created by Josué Chávez on 18/01/2017.
 */

public interface ITokenInputPresenter
{
    void setInitialViewState();
    void sendValidationToken(String pToken);
    void buildSentText(String phone);
    void retypePhoneNumber(boolean retypePhone);
    void setConfirmedPhone(boolean pConfirmedPhone);
    void setConfirmedCountry(boolean pConfirmedCountry);
}
