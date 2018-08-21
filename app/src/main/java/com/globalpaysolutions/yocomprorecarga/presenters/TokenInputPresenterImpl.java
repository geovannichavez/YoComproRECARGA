package com.globalpaysolutions.yocomprorecarga.presenters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.TokenInputInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.TokenInputListener;
import com.globalpaysolutions.yocomprorecarga.models.ErrorResponseViewModel;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.ValidateLocalSmsResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ITokenInputPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.TokenInputView;
import com.google.firebase.auth.FirebaseUser;
import com.onesignal.OneSignal;

import java.net.SocketTimeoutException;

/**
 * Created by Josué Chávez on 18/01/2017.
 */

public class TokenInputPresenterImpl implements ITokenInputPresenter, TokenInputListener
{
    private static final String TAG = TokenInputPresenterImpl.class.getSimpleName();

    private TokenInputView mView;
    private Context mContext;
    private TokenInputInteractor mInteractor;
    private UserData mUserData;
    private boolean is3Dcompatible;

    public TokenInputPresenterImpl(TokenInputView pView, AppCompatActivity pActivity, Context pContext)
    {
        mView = pView;
        mContext = pContext;
        mInteractor = new TokenInputInteractor(mContext);
        mUserData = UserData.getInstance(mContext);
        is3Dcompatible = mUserData.Is3DCompatibleDevice();
    }

    @Override
    public void setInitialViewState()
    {
        mView.initialViewsState();
        //mView.setCallcenterContactText();
        mView.setClickListeners();
    }

    @Override
    public void validateSmsToken(String pToken)
    {
        mView.showLoading();

        if(TextUtils.equals(UserData.getInstance(mContext).getAuthModeSelected(), Constants.LOCAL))
        {
            mInteractor.validateSmsTokenLocalAuth(this, pToken);
        }
        else
        {
            mInteractor.sendTokenValidation(this, pToken);
        }
    }

    @Override
    public void buildSentText(String phone)
    {
        mView.setCodeSentLabelText(phone);
    }

    @Override
    public void retypePhoneNumber(boolean retypePhone)
    {
        mView.navigatePhoneValidation(retypePhone);
    }

    @Override
    public void setConfirmedPhone(boolean pConfirmedPhone)
    {
        mInteractor.setConfirmedPhone(pConfirmedPhone);
    }

    @Override
    public void setConfirmedCountry(boolean pConfirmedCountry)
    {
        mInteractor.setConfirmedCountry(pConfirmedCountry);
    }

    @Override
    public void onError(int pCodeStatus, Throwable pThrowable)
    {
        mView.dismissLoading();
        ProcessErrorMessage(pCodeStatus, pThrowable);
    }

    @Override
    public void onValidationTokenResult(SimpleMessageResponse pResponse)
    {
        mView.vibrateOnSuccess();
        mView.dismissLoading();
        mInteractor.setConfirmedCountry(true);
        mInteractor.setConfirmedPhone(true);

        //Sets tag for OneSignal
        OneSignal.sendTag(Constants.ONESIGNAL_USER_TAG_KEY, mUserData.GetMsisdn());

        mView.navigateHome(is3Dcompatible);
    }

    @Override
    public void onValidationTokenLocalResult(ValidateLocalSmsResponse response)
    {
        try
        {
            mUserData.saveAuthenticationKey(response.getAuthenticationKey());
            mInteractor.setConfirmedCountry(true);
            mInteractor.setConfirmedPhone(true);

            if(response.getCountryID() > 0)
                mUserData.saveCountryID(String.valueOf(response.getCountryID()));

            mInteractor.anonymousAuthFirebase(this, response);

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on onValidationTokenLocalResult: " + ex.getMessage());
        }
    }

    @Override
    public void onFirebaseUserAuthSuccess(FirebaseUser user, ValidateLocalSmsResponse response)
    {
        try
        {
            Log.i(TAG, "Anonymous Firebase user: " + user.getUid() + "; " + user.getDisplayName());

            //Saves user's phone as AuthID
            UserData.getInstance(mContext).saveAuthData(response.getPhone(), "");

            //If nickname comes empty, is a new user registration. Must complete profile
            if(TextUtils.isEmpty(response.getNickname()))
            {
                mUserData.hasSetNickname(false);
                mView.navigateCompleteProfile();
            }
            else
            {
                mUserData.saveNickname(response.getNickname());
                UserData.getInstance(mContext).hasAuthenticated(true);
                UserData.getInstance(mContext).hasSetNickname(true);

                UserData.getInstance(mContext).saveUserGeneralInfo(response.getFirstName(), response.getLastName(), null,
                        UserData.getInstance(mContext).getUserPhone());

                mView.navigateHome(is3Dcompatible);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onFirebaseUserAuthError()
    {
        ProcessErrorMessage(0, null);
    }

    private void ProcessErrorMessage(int pCodeStatus, Throwable pThrowable)
    {
        ErrorResponseViewModel errorResponse = new ErrorResponseViewModel();

        try
        {
            if (pThrowable != null)
            {
                if (pThrowable instanceof SocketTimeoutException)
                {
                    String Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    String Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    String Button = mContext.getString(R.string.button_accept);

                    errorResponse.setTitle(Titulo);
                    errorResponse.setLine1(Linea1);
                    errorResponse.setAcceptButton(Button);
                    mView.cleanFields();
                    mView.showErrorMessage(errorResponse);

                }
                else
                {
                    String Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                    String Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                    String Button = mContext.getString(R.string.button_accept);

                    errorResponse.setTitle(Titulo);
                    errorResponse.setLine1(Linea1);
                    errorResponse.setAcceptButton(Button);
                    mView.cleanFields();
                    mView.showErrorMessage(errorResponse);
                }
            }
            else if(pCodeStatus != 0)
            {
                String Titulo;
                String Linea1;
                String Button;

                switch (pCodeStatus)
                {
                    case 403:
                        Titulo = mContext.getString(R.string.error_title_incorrect_token);
                        Linea1 = mContext.getString(R.string.error_label_incorrect_token);
                        Button = mContext.getString(R.string.button_accept);

                        errorResponse.setTitle(Titulo);
                        errorResponse.setLine1(Linea1);
                        errorResponse.setAcceptButton(Button);
                        mView.cleanFields();
                        mView.showErrorMessage(errorResponse);
                        break;
                    case 500:
                        Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                        Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                        Button = mContext.getString(R.string.button_accept);

                        errorResponse.setTitle(Titulo);
                        errorResponse.setLine1(Linea1);
                        errorResponse.setAcceptButton(Button);
                        mView.cleanFields();
                        mView.showErrorMessage(errorResponse);
                        break;
                    default:
                        Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                        Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                        Button = mContext.getString(R.string.button_accept);

                        errorResponse.setTitle(Titulo);
                        errorResponse.setLine1(Linea1);
                        errorResponse.setAcceptButton(Button);
                        mView.showErrorMessage(errorResponse);
                        break;
                }
            }
            else
            {
                String Titulo = mContext.getString(R.string.error_title_something_went_wrong);
                String Linea1 = mContext.getString(R.string.error_content_something_went_wrong_try_again);
                String Button = mContext.getString(R.string.button_accept);

                errorResponse.setTitle(Titulo);
                errorResponse.setLine1(Linea1);
                errorResponse.setAcceptButton(Button);
                mView.cleanFields();
                mView.showErrorMessage(errorResponse);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
