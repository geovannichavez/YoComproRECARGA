package com.globalpaysolutions.yocomprorecarga.presenters.interfaces;

import android.content.Intent;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Josué Chávez on 28/06/2017.
 */

public interface IAuthenticatePresenter
{
    void setupFacebookAuth(LoginButton pLoginButton);
    void onActivityResult(int pRequestCode, int pResultCode, Intent pData);
}
