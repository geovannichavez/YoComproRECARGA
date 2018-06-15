package com.globalpaysolutions.yocomprorecarga.presenters;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.internal.Validate;
import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.interactors.ValidatePhoneInteractor;
import com.globalpaysolutions.yocomprorecarga.interactors.ValidatePhoneListener;
import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.Country;
import com.globalpaysolutions.yocomprorecarga.models.ErrorResponseViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterClientResponse;
import com.globalpaysolutions.yocomprorecarga.presenters.interfaces.IValidatePhonePresenter;
import com.globalpaysolutions.yocomprorecarga.utils.Constants;
import com.globalpaysolutions.yocomprorecarga.utils.UserData;
import com.globalpaysolutions.yocomprorecarga.views.ValidatePhoneView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import junit.framework.Assert;

import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Set;

import static com.flurry.sdk.eh.i;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public class ValidatePhonePresenterImpl implements IValidatePhonePresenter, ValidatePhoneListener
{
    private static final String TAG = ValidatePhonePresenterImpl.class.getSimpleName();

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
    public void requestToken(String pPhoneNumber, String authType)
    {
        try
        {
            String simplePhone = new StringBuilder(pPhoneNumber).insert(pPhoneNumber.length()-4, "-").toString();
            userData.saveSimpleUserPhone(simplePhone);

            Country country = userData.getSelectedCountry();
            String msisdn = country.getPhoneCode() + pPhoneNumber;
            String countryID = country.getCode();

            this.View.showLoading();

            //If auth type is local, calls 'registerPhone' endpoint
            if(TextUtils.equals(Constants.LOCAL, authType))
            {
                UserData.getInstance(context).saveAuthModeSelected(Constants.LOCAL);
                this.interactor.authLocalUser(this, msisdn, countryID);
            }
            else
            {
                this.interactor.validatePhone(this, msisdn, countryID);
            }


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
    public void onError(int pCodeStatus, Throwable pThrowable, String pRequiredVersion, String rawResponse)
    {
        try
        {
            this.View.hideLoading();
            JsonObject  jsonObject =  (JsonObject) new JsonParser().parse(rawResponse);

            if(jsonObject.has("existsPhone"))
            {
                if(jsonObject.get("existsPhone").getAsBoolean())
                {
                    ErrorResponseViewModel model = new ErrorResponseViewModel();
                    model.setTitle(context.getString(R.string.title_phone_already_exists));
                    model.setLine1(context.getString(R.string.label_phone_already_exists));
                    model.setAcceptButton(context.getString(R.string.button_accept));
                    View.showGenericMessage(model);
                }
            }
            else
            {
                ProcessErrorMessage(pCodeStatus, pThrowable, pRequiredVersion);
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onGetCountriesSuccess(Countries pCountries)
    {
        this.View.hideLoading();
        this.View.renderCountries(pCountries);
    }

    @Override
    public void onRequestPhoneValResult(RegisterClientResponse pResponse, int codeStatus )
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
            this.View.navigateTokenInput(pResponse, "");
        }

    }

    @Override
    public void onAuthLocalSuccess(RegisterClientResponse response, int code)
    {
        this.View.hideLoading();
        if(code == 201)
        {
            ErrorResponseViewModel dialog = new ErrorResponseViewModel();
            dialog.setTitle(context.getString(R.string.title_await_please));
            dialog.setLine1(response.getSecondsRemaining());
            dialog.setAcceptButton(context.getString(R.string.button_accept));
            this.View.showGenericMessage(dialog);
        }
        else
        {
            //If user is already registered with GOOGLE or FACEBOOK, saves
            // it's value, if exists registered Locally, will return false
            if(response.isExistsPhone())
            {
                //TODO: Mostrar mensaje de usuario con registrado
                ErrorResponseViewModel dialog = new ErrorResponseViewModel();
                dialog.setTitle(context.getString(R.string.title_user_already_exists));
                dialog.setLine1(context.getString(R.string.label_user_already_exists));
                dialog.setAcceptButton(context.getString(R.string.button_accept));
                View.showGenericMessage(dialog);
            }
            else
            {
                //Validate phone via SMS
                if(TextUtils.equals(UserData.getInstance(context).getAuthModeSelected(), Constants.LOCAL))
                    this.View.navigateTokenInput(response, Constants.LOCAL);
                else
                    this.View.navigateTokenInput(response, "");
            }
        }

    }

    private void ProcessErrorMessage(int pCodeStatus, Throwable pThrowable, String pRequiredVersion)
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

                if(pCodeStatus == 426)
                {
                    ErrorResponseViewModel dialog = new ErrorResponseViewModel();
                    dialog.setTitle(context.getString(R.string.title_update_required));
                    dialog.setLine1(String.format(context.getString(R.string.content_update_required), pRequiredVersion));
                    dialog.setAcceptButton(context.getString(R.string.button_accept));
                    this.View.showGenericMessage(dialog);
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
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
