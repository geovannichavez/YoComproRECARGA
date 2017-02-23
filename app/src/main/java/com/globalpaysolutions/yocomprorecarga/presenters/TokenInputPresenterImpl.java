package com.globalpaysolutions.yocomprorecarga.presenters;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.TokenInputInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.TokenInputListener;
import com.globalpaysolutions.yocomprorecarga.models.ErrorResponseViewModel;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.ITokenInputPresenter;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.TokenInputView;

import java.net.SocketTimeoutException;

/**
 * Created by Josué Chávez on 18/01/2017.
 */

public class TokenInputPresenterImpl implements ITokenInputPresenter, TokenInputListener
{
    private TokenInputView mView;
    private Context mContext;
    private TokenInputInteractor mInteractor;

    public TokenInputPresenterImpl(TokenInputView pView, AppCompatActivity pActivity, Context pContext)
    {
        mView = pView;
        mContext = pContext;
        mInteractor = new TokenInputInteractor(mContext);
    }

    @Override
    public void setInitialViewState()
    {
        mView.initialViewsState();
    }

    @Override
    public void sendValidationToken(String pToken)
    {
        mView.showLoading();
        mInteractor.sendTokenValidation(this, pToken);
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

        mView.navigateHome();
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
