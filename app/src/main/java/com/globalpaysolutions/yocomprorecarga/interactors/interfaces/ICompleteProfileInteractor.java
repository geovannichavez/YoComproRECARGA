package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import com.globalpaysolutions.yocomprorecarga.interactors.CompleteProfileListener;

public interface ICompleteProfileInteractor
{
    void completeProfileLocalAuth(CompleteProfileListener listener, String firstname, String lastname, String nickname);
}