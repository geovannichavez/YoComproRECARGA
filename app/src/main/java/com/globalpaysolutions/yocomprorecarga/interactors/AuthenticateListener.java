package com.globalpaysolutions.yocomprorecarga.interactors;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.globalpaysolutions.yocomprorecarga.models.api.AuthenticateResponse;

/**
 * Created by Josué Chávez on 29/06/2017.
 */

public interface AuthenticateListener
{
    void onFacebookEmailSuccess(String pEmail, LoginResult pLoginResult);
    void onFacebookEmailError();
    void onGraphLoginSuccess(LoginResult pLoginResult);
    void onGraphLoginCancel();
    void onGraphLoginError(FacebookException pException);

    void onFirebaseAuthSuccess(String pEmail);
    void onFirebaseAuthError();

    void onCurrentProfileChanged(Profile pOldProfile, Profile pCurrentProfile);
    void onCurrentAccessTokenChanged(AccessToken pOldAccessToken, AccessToken pCurrentAccessToken);

    void onAuthenticateConsumerSuccess(AuthenticateResponse pResponse);
    void onAuthenticateConsumerError(int pCodeStatus, Throwable pThrowable);
}
