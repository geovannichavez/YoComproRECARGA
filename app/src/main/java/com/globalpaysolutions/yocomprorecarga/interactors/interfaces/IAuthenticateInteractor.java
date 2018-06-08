package com.globalpaysolutions.yocomprorecarga.interactors.interfaces;

import android.content.Intent;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.globalpaysolutions.yocomprorecarga.interactors.AuthenticateListener;
import com.globalpaysolutions.yocomprorecarga.models.Consumer;

/**
 * Created by Josué Chávez on 29/06/2017.
 */

public interface IAuthenticateInteractor
{
    void initializeFacebook(AuthenticateListener pListener);

    void authenticateFirebaseUser(AuthenticateListener pListener, String providerToken, String pEmail);
    void logoutFirebaseUser();

    void authenticateFacebookUser(AuthenticateListener pListener, LoginButton pLoginButton);
    void logoutFacebookUser();

    void requestUserEmail(AuthenticateListener pListener, LoginResult pLoginResult);
    void onActivityResult(int pRequestCode, int pResultCode, Intent pData);
    void authenticateUser(AuthenticateListener pListener, Consumer pAuthentictionReqBody);
}
