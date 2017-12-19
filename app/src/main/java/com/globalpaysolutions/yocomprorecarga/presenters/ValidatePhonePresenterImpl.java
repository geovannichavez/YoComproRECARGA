package com.globalpaysolutions.yocomprorecarga.presenters;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.ValidatePhoneInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.ValidatePhoneListener;
import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.Country;
import com.globalpaysolutions.yocomprorecarga.models.ErrorResponseViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterClientResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IValidatePhonePresenter;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.ValidatePhoneView;

import java.net.SocketTimeoutException;

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
    public void requestToken(String pPhoneNumber)
    {
        try
        {
            String simplePhone = new StringBuilder(pPhoneNumber).insert(pPhoneNumber.length()-4, "-").toString();
            userData.saveSimpleUserPhone(simplePhone);

            Country country = userData.getSelectedCountry();
            String msisdn = country.getPhoneCode() + pPhoneNumber;
            String countryID = country.getCode();

            this.View.showLoading();
            this.interactor.validatePhone(this, msisdn, countryID);
        }
        catch (Exception ex)  { ex.printStackTrace();   }
    }

    @Override
    public void savePreselectedCountry(Country pCountry)
    {
        userData.savePreselectedCountryInfo(pCountry.getCountrycode(), pCountry.getPhoneCode(), pCountry.getCode(), pCountry.getName());
    }

    @Override
    public void saveUserGeneralData(String pPhone, int pConsumerID)
    {
        try
        {
            Country country = userData.getSelectedCountry();
            this.interactor.saveUserGeneralInfo(country.getCode(), country.getCountrycode(), country.getName(), country.getPhoneCode(), pPhone, pConsumerID);
        }
        catch (Exception ex) { ex.printStackTrace();    }
    }

    @Override
    public void setSelectedCountry(Country pCountry)
    {
        if(pCountry != null)
        {
            View.setSelectedCountry(pCountry);
        }
        else
        {
            View.retypePhoneView();
            Country country = userData.getSelectedCountry();
            View.setSelectedCountry(country);
        }
    }

    @Override
    public void setTypedPhone()
    {
        View.setTypedPhone(userData.getUserSimplePhone());
    }

    @Override
    public void loadBackground()
    {
        View.loadBackground();
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
    public void onRequestPhoneValResult(RegisterClientResponse pResponse, int codeStatus)
    {

        this.View.hideLoading();
        if(codeStatus == 201)
        {
            ErrorResponseViewModel dialog = new ErrorResponseViewModel();
            dialog.setTitle(context.getString(R.string.title_await_please));
            dialog.setLine1(pResponse.getSecondsRemaining());
            dialog.setAcceptButton(context.getString(R.string.button_accept));
            this.View.showGenericMessage(dialog);
        }
        else
        {
            this.View.navigateTokenInput(pResponse);
        }

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
                    this.View.showGenericMessage(errorResponse);

                }
                else
                {
                    String Titulo = context.getString(R.string.error_title_something_went_wrong);
                    String Linea1 = context.getString(R.string.error_content_something_went_wrong_try_again);
                    String Button = context.getString(R.string.button_accept);

                    errorResponse.setTitle(Titulo);
                    errorResponse.setLine1(Linea1);
                    errorResponse.setAcceptButton(Button);
                    this.View.showGenericMessage(errorResponse);
                }
            }
            else
            {
                String Titulo = context.getString(R.string.error_title_something_went_wrong);
                String Linea1 = context.getString(R.string.error_content_something_went_wrong_try_again);
                String Button = context.getString(R.string.button_accept);

                /*if(pCodeStatus == 500 && !TextUtils.equals(pSecondsRemaining, ""))
                {
                    Titulo = context.getString(R.string.error_title_sms_await_title);
                    Linea1 = pSecondsRemaining;
                }*/

                errorResponse.setTitle(Titulo);
                errorResponse.setLine1(Linea1);
                errorResponse.setAcceptButton(Button);
                this.View.showGenericMessage(errorResponse);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
