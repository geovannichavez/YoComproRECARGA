package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.TokenInputListener;

/**
 * Created by Josué Chávez on 18/01/2017.
 */

public interface ITokenInputInteractor
{
    void sendTokenValidation(TokenInputListener pListener, String pToken);
    void setConfirmedPhone(boolean pConfirmed);
    void setConfirmedCountry(boolean pConfirmedCountry);
}
