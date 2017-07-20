package com.globalpaysolutions.yocomprorecarga.presenters;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.ValidatePhoneInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.ValidatePhoneListener;
import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.ErrorResponseViewModel;
import com.globalpaysolutions.yocomprorecarga.models.SimpleMessageResponse;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterClientResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IValidatePhonePresenter;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.ValidatePhoneView;

import java.net.SocketTimeoutException;
import java.util.UUID;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public class ValidatePhonePresenterImpl implements IValidatePhonePresenter, ValidatePhoneListener
{
    private ValidatePhoneView View;
    private Context context;
    private ValidatePhoneInteractor interactor;
    private UserData userData;

    public ValidatePhonePresenterImpl(ValidatePhoneView pView, AppCompatActivity pActivity, Context pContext)
    {
        this.View = pView;
        this.context = pContext;
        this.interactor = new ValidatePhoneInteractor(pContext);
        this.userData = UserData.getInstance(context);
    }

    @Override
    public void setInitialViewState()
    {
        this.View.initialViewsStates();
    }

    @Override
    public void fetchCountries()
    {
        this.View.showLoading();
        this.interactor.fethCountries(this);
    }

    @Override
    public void requestToken(String pMsisdn, String pCountryID)
    {
        this.View.showLoading();
        this.interactor.validatePhone(this, pMsisdn, pCountryID);
    }

    @Override
    public void saveUserGeneralData(String pPhoneCode, String pCountryID, String pIso3Code, String pCountryName, String pPhone, int pConsumerID)
    {
        this.interactor.saveUserGeneralInfo(pCountryID, pIso3Code, pCountryName, pPhoneCode, pPhone, pConsumerID);
    }

    @Override
    public void onError(int pCodeStatus, Throwable pThrowable)
    {
        this.View.hideLoading();
        ProcessErrorMessage(pCodeStatus, pThrowable);
    }

    @Override
    public void onGetCountriesSuccess(Countries pCountries)
    {
        this.View.hideLoading();
        this.View.renderCountries(pCountries);
    }

    @Override
    public void onRequestPhoneValResult(RegisterClientResponse pResponse)
    {
        this.View.hideLoading();
        this.View.navigateTokenInput(pResponse);

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
                    String Titulo = context.getString(R.string.error_title_something_went_wrong);
                    String Linea1 = context.getString(R.string.error_content_something_went_wrong_try_again);
                    String Button = context.getString(R.string.button_accept);

                    errorResponse.setTitle(Titulo);
                    errorResponse.setLine1(Linea1);
                    errorResponse.setAcceptButton(Button);
                    this.View.showErrorMessage(errorResponse);

                }
                else
                {
                    String Titulo = context.getString(R.string.error_title_something_went_wrong);
                    String Linea1 = context.getString(R.string.error_content_something_went_wrong_try_again);
                    String Button = context.getString(R.string.button_accept);

                    errorResponse.setTitle(Titulo);
                    errorResponse.setLine1(Linea1);
                    errorResponse.setAcceptButton(Button);
                    this.View.showErrorMessage(errorResponse);
                }
            }
            else
            {
                String Titulo = context.getString(R.string.error_title_something_went_wrong);
                String Linea1 = context.getString(R.string.error_content_something_went_wrong_try_again);
                String Button = context.getString(R.string.button_accept);

                errorResponse.setTitle(Titulo);
                errorResponse.setLine1(Linea1);
                errorResponse.setAcceptButton(Button);
                this.View.showErrorMessage(errorResponse);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
