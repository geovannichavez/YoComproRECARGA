package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.AuthenticateInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.AuthenticateListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.FacebookConsumer;
import com.globalpaysolutions.yocomprorecarga.models.api.AuthenticateResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IAuthenticatePresenter;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.AuthenticateView;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

/**
 * Created by Josué Chávez on 28/06/2017.
 */

public class AuthenticatePresenterImpl implements IAuthenticatePresenter, AuthenticateListener
{
    private static final String TAG = AuthenticatePresenterImpl.class.getSimpleName();

    private Context mContext;
    private UserData mUserData;
    private AuthenticateView mView;
    private AuthenticateInteractor mInteractor;


    public AuthenticatePresenterImpl(Context pContext, AuthenticateView pView, AppCompatActivity pActivity)
    {
        this.mContext = pContext;
        this.mUserData = UserData.getInstance(mContext);
        this.mView = pView;
        this.mInteractor = new AuthenticateInteractor(mContext);
    }

    @Override
    public void setupFacebookAuth(LoginButton pLoginButton)
    {
        Log.i(TAG, "Facebook authentication started");
        mInteractor.initializeFacebook(this);
        pLoginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        mInteractor.authenticateFacebookUser(this, pLoginButton);
    }

    @Override
    public void onActivityResult(int pRequestCode, int pResultCode, Intent pData)
    {
        Log.i(TAG, "onActivityResult: resultCode = : " + String.valueOf(pRequestCode));
        mInteractor.onActivityResult(pRequestCode, pResultCode, pData);
    }

    @Override
    public void onFacebookEmailSuccess(String pEmail, LoginResult pLoginResult)
    {
        try
        {
            //Authenticates user with Firebase
            mInteractor.authenticateFirebaseUser(this, pLoginResult.getAccessToken(), pEmail);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onFacebookEmailError()
    {
        mInteractor.logoutFacebookUser();
    }

    @Override
    public void onGraphLoginSuccess(LoginResult pLoginResult)
    {
        Log.i(TAG, "Facebook email request started");
        mView.showLoadingDialog(mContext.getString(R.string.label_loading_please_wait));
        mInteractor.requestUserEmail(this, pLoginResult);

    }

    @Override
    public void onGraphLoginCancel()
    {
        Log.i(TAG, "Facebook email request cancelled");
    }

    @Override
    public void onGraphLoginError(FacebookException pException)
    {
        mInteractor.logoutFacebookUser();
        DialogViewModel dialog = new DialogViewModel();
        dialog.setTitle(mContext.getString(R.string.error_title_something_went_wrong));
        dialog.setLine1(mContext.getString(R.string.error_content_facebook_graph));
        dialog.setAcceptButton(mContext.getString(R.string.button_accept));
        mView.showGenericDialog(dialog);
        Log.i(TAG, "Facebook email request failed");
    }

    @Override
    public void onFirebaseAuthSuccess(String pEmail)
    {
        try
        {
            Profile profile = Profile.getCurrentProfile();

            //Validates all fields from Profile
            FacebookConsumer facebookConsumer = new FacebookConsumer();
            String firstname = (!TextUtils.isEmpty(profile.getFirstName())) ? profile.getFirstName() : "NotFound";
            String lastname = (!TextUtils.isEmpty(profile.getLastName())) ? profile.getLastName() : "NotFound";
            String facebookUrl = (!TextUtils.isEmpty(profile.getLinkUri().toString())) ? profile.getLinkUri().toString() : "NotFound";
            String profileId = (!TextUtils.isEmpty(profile.getId())) ? profile.getId() : "NotFound";
            String middlename = (!TextUtils.isEmpty(profile.getMiddleName())) ? profile.getMiddleName() : "NotFound";
            String name = (!TextUtils.isEmpty(profile.getName())) ? profile.getName() : "NotFound";

            //REGISTER CONSUMER
            facebookConsumer.setFirstName(firstname);
            facebookConsumer.setLastName(lastname);
            facebookConsumer.setDeviceID(mUserData.GetDeviceID());
            facebookConsumer.setURL(facebookUrl);
            facebookConsumer.setProfileID(profileId);
            facebookConsumer.setEmail(pEmail);
            facebookConsumer.setMiddleName(middlename);
            facebookConsumer.setUserID(profileId);
            mUserData.saveFacebookData(profileId, facebookUrl);
            mUserData.saveFacebookFullname(name);

            //Registers user on RecarGO! API
            mInteractor.authenticateUser(this, facebookConsumer);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onFirebaseAuthError()
    {
        try
        {
            mInteractor.logoutFirebaseUser();
            mInteractor.logoutFacebookUser();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onCurrentProfileChanged(Profile pOldProfile, Profile pCurrentProfile)
    {
        //TODO: quitar el toast
        //mView.showGenericToast("Profile changed");
    }

    @Override
    public void onCurrentAccessTokenChanged(AccessToken pOldAccessToken, AccessToken pCurrentAccessToken)
    {
        //TODO: quitar el toast
        //mView.showGenericToast("Access token changed");
    }

    @Override
    public void onAuthenticateConsumerSuccess(AuthenticateResponse pResponse)
    {
        mView.hideLoadingDialog();
        mUserData.saveUserGeneralInfo(pResponse.getFirstName(), pResponse.getLastName(), pResponse.getEmail(), pResponse.getPhone());
        mUserData.saveAuthenticationKey(pResponse.getAuthenticationKey());
        mUserData.saveNickname(pResponse.getNickname());
        mUserData.hasAuthenticated(true);

        if(mUserData.getUserPhone() == null || TextUtils.isEmpty(mUserData.getUserPhone()))
            mView.navigateValidatePhone();
        else if(mUserData.getNickname() == null || TextUtils.isEmpty(mUserData.getNickname()))
            mView.navigateSetNickname();
        else
            mView.navigateHome();

    }

    @Override
    public void onAuthenticateConsumerError(int pCodeStatus, Throwable pThrowable)
    {
        mInteractor.logoutFacebookUser();
        mInteractor.logoutFirebaseUser();
        mView.hideLoadingDialog();
        this.processErrorMessage(pCodeStatus, pThrowable);
    }

    /*
    *
    *
    * OTHER METHODS
    *
    *
    */

    private void processErrorMessage(int pCodeStatus, Throwable pThrowable)
    {
        DialogViewModel errorResponse = new DialogViewModel();

        try
        {
            String Titulo;
            String Linea1;
            String Button;

            if (pThrowable != null)
            {
                if (pThrowable instanceof SocketTimeoutException)
                {
                    Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = mContext.getString(R.string.button_accept);
                }
                else if (pThrowable instanceof IOException)
                {
                    Titulo = mContext.getString(R.string.error_title_internet_connecttion);
                    Linea1 = mContext.getString(R.string.error_content_internet_connecttion);
                    Button = mContext.getString(R.string.button_accept);
                }
                else
                {
                    Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = mContext.getString(R.string.button_accept);
                }
            }
            else
            {
                if(pCodeStatus == 401)
                {
                    Titulo = mContext.getString(R.string.error_title_vendor_not_found);
                    Linea1 = mContext.getString(R.string.error_content_vendor_not_found_line);
                    Button = mContext.getString(R.string.button_accept);
                }
                else
                {
                    Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    Button = mContext.getString(R.string.button_accept);
                }
            }

            errorResponse.setTitle(Titulo);
            errorResponse.setLine1(Linea1);
            errorResponse.setAcceptButton(Button);
            this.mView.showGenericDialog(errorResponse);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}