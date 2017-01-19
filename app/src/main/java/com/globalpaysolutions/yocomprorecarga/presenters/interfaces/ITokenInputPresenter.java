package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

/**
 * Created by Josué Chávez on 18/01/2017.
 */

public interface ITokenInputPresenter
{
    void setInitialViewState();
    void sendValidationToken(String pMsisdn, String pToken);
    void setConfirmedPhone(boolean pConfirmedPhone);
    void setConfirmedCountry(boolean pConfirmedCountry);
}
