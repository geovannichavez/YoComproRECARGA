package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.AuthenticateInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.AuthenticateListener;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.Consumer;
import com.globalpaysolutions.yocomprorecarga.models.api.AuthenticateResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IAuthenticatePresenter;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.AuthenticateView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

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
    private AppCompatActivity mActivity;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleAccount;
    private int RC_SIGN_IN = 11;

    public AuthenticatePresenterImpl(Context pContext, AuthenticateView pView, AppCompatActivity pActivity)
    {
        this.mContext = pContext;
        this.mUserData = UserData.getInstance(mContext);
        this.mView = pView;
        this.mInteractor = new AuthenticateInteractor(mContext);
        this.mActivity = pActivity;
    }

    @Override
    public void checkPlayServices()
    {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(mActivity);
        if (result != ConnectionResult.SUCCESS)
        {
            //Any random request code
            int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

            if (googleAPI.isUserResolvableError(result))
            {
                googleAPI.getErrorDialog(mActivity, result, PLAY_SERVICES_RESOLUTION_REQUEST).show();
                mView.enableLoginFacebookButton(false);
            }
            else
            {
                mView.enableLoginFacebookButton(true);
            }
        }
    }

    @Override
    public void setupFacebookAuth(LoginButton pLoginButton)
    {
        UserData.getInstance(mContext).saveAuthModeSelected(Constants.FACEBOOK);
        Log.i(TAG, "Facebook authentication started");

        mInteractor.initializeFacebook(this);
        pLoginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        mInteractor.authenticateFacebookUser(this, pLoginButton);
    }

    @Override
    public void setupGoogleAuth()
    {
        try
        {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestServerAuthCode(Constants.GOOGLE_OAUTH_CLIENT_ID)
                    .requestServerAuthCode(Constants.GOOGLE_OAUTH_CLIENT_ID, false)
                    .requestIdToken(Constants.GOOGLE_OAUTH_CLIENT_ID)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on GoogleSignInOptions: "+ ex.getMessage());
        }
    }

    @Override
    public void checkGoogleSignedIn()
    {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mContext);
        if(account != null)
            Log.i(TAG, "User has been GoogleSigned in"); //TODO: Manejo de caso
    }

    @Override
    public void googleSignin()
    {
        UserData.getInstance(mContext).saveAuthModeSelected(Constants.GOOGLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i(TAG, "onActivityResult: resultCode = : " + String.valueOf(requestCode));
        mInteractor.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN)
        {
            // The Task returned from this call is always completed, no need to attach a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    public void onFacebookEmailSuccess(String pEmail, LoginResult pLoginResult)
    {
        try
        {
            //Authenticates user with Firebase
            mInteractor.authenticateFirebaseUser(this, pLoginResult.getAccessToken().getToken(), pEmail);
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
    public void onFirebaseAuthSuccess(String pEmail, String authProvider)
    {
        Consumer consumer = new Consumer();

        try
        {
            switch (authProvider)
            {
                case Constants.FACEBOOK:

                    Profile profile = Profile.getCurrentProfile();
                    String firstname = (!TextUtils.isEmpty(profile.getFirstName())) ? profile.getFirstName() : "NotFound";
                    String lastname = (!TextUtils.isEmpty(profile.getLastName())) ? profile.getLastName() : "NotFound";
                    String facebookUrl = (!TextUtils.isEmpty(profile.getLinkUri().toString())) ? profile.getLinkUri().toString() : "NotFound";
                    String profileId = (!TextUtils.isEmpty(profile.getId())) ? profile.getId() : "NotFound";
                    String middlename = (!TextUtils.isEmpty(profile.getMiddleName())) ? profile.getMiddleName() : "NotFound";
                    String name = (!TextUtils.isEmpty(profile.getName())) ? profile.getName() : "NotFound";

                    consumer.setFirstName(firstname);
                    consumer.setLastName(lastname);
                    consumer.setDeviceID(mUserData.getDeviceID());
                    consumer.setURL(facebookUrl);
                    consumer.setProfileID(profileId);
                    consumer.setEmail(pEmail);
                    consumer.setMiddleName(middlename);
                    consumer.setUserID(profileId);

                    break;

                case Constants.GOOGLE:
                    String googleUrl = (TextUtils.isEmpty(mGoogleAccount.getPhotoUrl().toString())) ? "NotFound" : mGoogleAccount.getPhotoUrl().toString();

                    mUserData.saveGooglePhotoUrl(googleUrl);

                    consumer.setFirstName(mGoogleAccount.getGivenName());
                    consumer.setLastName(mGoogleAccount.getFamilyName());
                    consumer.setDeviceID(mUserData.getDeviceID());
                    consumer.setURL("NotFound");
                    consumer.setProfileID(mGoogleAccount.getId());
                    consumer.setEmail(mGoogleAccount.getEmail());
                    consumer.setMiddleName("NotFound");
                    consumer.setUserID(mGoogleAccount.getId());

                    break;
            }

            consumer.setAuthProvider(mUserData.getAuthModeSelected());
            mUserData.saveAuthData(consumer.getProfileID(), consumer.getURL());

            String name = consumer.getFirstName() + " " + consumer.getLastName();

            //Saves user email in CrashLytics
            Crashlytics.setUserEmail(pEmail);
            Crashlytics.setUserName(name);

            mUserData.saveAuthProviderFullname(name);

            //Registers user on RecarGO! API
            mInteractor.authenticateUser(this, consumer);
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

    }

    @Override
    public void onCurrentAccessTokenChanged(AccessToken pOldAccessToken, AccessToken pCurrentAccessToken)
    {

    }

    @Override
    public void onAuthenticateConsumerSuccess(AuthenticateResponse pResponse)
    {
        mView.hideLoadingDialog();
        mUserData.saveUserGeneralInfo(pResponse.getFirstName(), pResponse.getLastName(), pResponse.getEmail(), pResponse.getPhone());
        mUserData.saveAuthenticationKey(pResponse.getAuthenticationKey());
        mUserData.saveNickname(pResponse.getNickname());
        mUserData.hasAuthenticated(true);

        //Saves if user has nickname
        if(pResponse.getNickname() != null || !TextUtils.equals(pResponse.getNickname(), ""))
            mUserData.hasSetNickname(true);
        else
            mUserData.hasSetNickname(false);


        if(mUserData.getUserPhone() == null || TextUtils.isEmpty(mUserData.getUserPhone()))
        {
            mView.navigateValidatePhone();
        }
        else if(mUserData.getNickname() == null || TextUtils.isEmpty(mUserData.getNickname()))
        {
            mView.navigateSetNickname();
        }
        else
        {
            mView.navigateHome();
        }


    }

    @Override
    public void onAuthenticateConsumerError(int pCodeStatus, Throwable pThrowable, String pRequiredVersion)
    {
        mInteractor.logoutFacebookUser();
        mInteractor.logoutFirebaseUser();
        mView.hideLoadingDialog();
        this.processErrorMessage(pCodeStatus, pThrowable, pRequiredVersion);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try
        {
            mGoogleAccount = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            if(mGoogleAccount != null)
            {
                String tokenId = mGoogleAccount.getIdToken();
                mInteractor.authenticateFirebaseUser(this, tokenId, mGoogleAccount.getEmail());
            }

        }
        catch (ApiException e)
        {
            // The ApiException status code indicates the detailed failure reason.
            Log.i(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    /*
    *
    *
    * OTHER METHODS
    *
    *
    */

    private void processErrorMessage(int pCodeStatus, Throwable pThrowable, String pRequiredVersion)
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
                else if(pCodeStatus == 426)
                {

                    Titulo = mContext.getString(R.string.title_update_required);
                    Linea1 = String.format(mContext.getString(R.string.content_update_required), pRequiredVersion);
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