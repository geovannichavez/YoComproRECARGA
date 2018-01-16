package com.globalpaysolutions.yocomprorecarga.interactors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.coreui.BuildConfig;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.globalpaysolutions.yocomprorecarga.api.ApiClient;
import com.globalpaysolutions.yocomprorecarga.api.ApiInterface;
import com.globalpaysolutions.yocomprorecarga.interactors.interfaces.IAuthenticateInteractor;
import com.globalpaysolutions.yocomprorecarga.models.FacebookConsumer;
import com.globalpaysolutions.yocomprorecarga.models.SimpleResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.AuthenticaReqBody;
import com.globalpaysolutions.yocomprorecarga.models.api.AuthenticateResponse;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Josué Chávez on 29/06/2017.
 */

public class AuthenticateInteractor implements IAuthenticateInteractor
{
    private static final String TAG = AuthenticateInteractor.class.getSimpleName();

    private Context mContext;

    //Facebook
    private CallbackManager mCallbackManager;

    //Firebase
    private FirebaseAuth mFirebaseAuth;


    public AuthenticateInteractor(Context pContext)
    {
        this.mContext = pContext;
        this.mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void initializeFacebook(final AuthenticateListener pListener)
    {
        mCallbackManager = CallbackManager.Factory.create();

        ProfileTracker mProfileTracker = new ProfileTracker()
        {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile)
            {
                pListener.onCurrentProfileChanged(oldProfile, currentProfile);
            }
        };

        AccessTokenTracker mAccessTokenTracker = new AccessTokenTracker()
        {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
            {
                if (currentAccessToken == null)
                {
                    pListener.onCurrentAccessTokenChanged(oldAccessToken, currentAccessToken);
                }
            }
        };

    }

    @Override
    public void authenticateFirebaseUser(final AuthenticateListener pListener, final AccessToken pAcessToken, final String pEmail)
    {
        try
        {
            AuthCredential credential = FacebookAuthProvider.getCredential(pAcessToken.getToken());

            mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                        if(user != null)
                        {
                            Log.i(TAG, String.format("FirebaseAuth: %1$s - %2$s)", user.getDisplayName(), user.getUid()));
                            pListener.onFirebaseAuthSuccess(pEmail);
                        }
                    }
                    else
                    {
                        Log.w(TAG, "FirebaseAuth failed", task.getException());
                        pListener.onFirebaseAuthError();
                    }
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void logoutFirebaseUser()
    {
        try
        {
            FirebaseAuth.getInstance().signOut();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void authenticateFacebookUser(final AuthenticateListener pListener, LoginButton pLoginButton)
    {
        pLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                pListener.onGraphLoginSuccess(loginResult);
                Log.i(TAG, "Facebook login success");
            }

            @Override
            public void onCancel()
            {
                pListener.onGraphLoginCancel();
                Log.i(TAG, "Facebook login canceled");
            }

            @Override
            public void onError(FacebookException error)
            {
                pListener.onGraphLoginError(error);
                Log.i(TAG, "Facebook login canceled");
            }
        });
    }

    @Override
    public void logoutFacebookUser()
    {
        try
        {
            LoginManager.getInstance().logOut();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void requestUserEmail(final AuthenticateListener pListener, final LoginResult pLoginResult)
    {
        GraphRequest mGraphRequest = GraphRequest.newMeRequest(pLoginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
        {
            @Override
            public void onCompleted(JSONObject me, GraphResponse response)
            {
                if (response.getError() == null)
                {
                    try
                    {
                        String facebookEmail = !me.isNull("email") ? me.getString("email") : "";
                        pListener.onFacebookEmailSuccess(facebookEmail, pLoginResult);
                        Log.i(TAG, "FacebookEmail: " + facebookEmail);
                    }
                    catch (JSONException ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    pListener.onFacebookEmailError();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email, name");
        mGraphRequest.setParameters(parameters);
        mGraphRequest.executeAsync();
    }

    @Override
    public void onActivityResult(int pRequestCode, int pResultCode, Intent pData)
    {
        mCallbackManager.onActivityResult(pRequestCode, pResultCode, pData);
    }

    @Override
    public void authenticateUser(final AuthenticateListener pListener, FacebookConsumer pAuthentictionReqBody)
    {
        AuthenticaReqBody requestBody = new AuthenticaReqBody();
        requestBody.setFirstName(pAuthentictionReqBody.getFirstName());
        requestBody.setLastName(pAuthentictionReqBody.getLastName());
        requestBody.setMiddleName(pAuthentictionReqBody.getMiddleName());
        requestBody.setEmail(pAuthentictionReqBody.getEmail());
        requestBody.setDeviceID(pAuthentictionReqBody.getDeviceID());
        requestBody.setUserID(pAuthentictionReqBody.getUserID());
        requestBody.setProfileID(pAuthentictionReqBody.getProfileID());
        requestBody.setURL(pAuthentictionReqBody.getURL());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<AuthenticateResponse> call = apiService.authenticateConsumer(requestBody,
                BuildConfig.VERSION_NAME, Constants.PLATFORM);

        call.enqueue(new Callback<AuthenticateResponse>()
        {
            @Override
            public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response)
            {
                if (response.isSuccessful())
                {
                    AuthenticateResponse authResult = response.body();
                    pListener.onAuthenticateConsumerSuccess(authResult);
                }
                else
                {
                    try
                    {
                        int codeResponse = response.code();

                        if(codeResponse == 426)
                        {
                            Gson gson = new Gson();
                            SimpleResponse errorResponse = gson.fromJson(response.errorBody().string(), SimpleResponse.class);
                            pListener.onAuthenticateConsumerError(codeResponse, null, errorResponse.getInternalCode());
                        }
                        else
                        {
                            pListener.onAuthenticateConsumerError(codeResponse, null, null);
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<AuthenticateResponse> call, Throwable t)
            {
                pListener.onAuthenticateConsumerError(0, t, null);
            }
        });
    }
}
