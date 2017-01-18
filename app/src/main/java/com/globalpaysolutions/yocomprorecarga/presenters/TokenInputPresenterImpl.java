package com.globalpaysolutions.yocomprorecarga.presenters;

import com.globalpaysolutions.yocomprorecarga.interactors.TokenInputListener;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ITokenInput;

/**
 * Created by Josué Chávez on 18/01/2017.
 */

public class TokenInputPresenterImpl implements ITokenInput, TokenInputListener
{
    @Override
    public void setInitialViewState()
    {

    }

    @Override
    public void sendValidationToken(String pMsisdn, String pToken)
    {

    }

    @Override
    public void setConfirmedPhone(boolean pConfirmedPhone)
    {

    }

    @Override
    public void setConfirmedCountry(boolean pConfirmedCountry)
    {

    }

    @Override
    public void onError(int pCodeStatus, Throwable pThrowable)
    {

    }

    @Override
    public void onValidationTokenResult(SimpleMessageResponse pResponse)
    {

    }
}
