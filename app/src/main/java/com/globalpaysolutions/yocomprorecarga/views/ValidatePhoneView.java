package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.Country;
import com.globalpaysolutions.yocomprorecarga.models.ErrorResponseViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.RegisterClientResponse;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public interface ValidatePhoneView
{
    void initialViewsStates();
    void retypePhoneView();
    void setTypedPhone(String phone);
    void showLoading();
    void hideLoading();
    void showErrorMessage(ErrorResponseViewModel pErrorMessage);
    void renderCountries(Countries pCountries);
    void navigateTokenInput(RegisterClientResponse pResponse);
    void setSelectedCountry(Country pSelected);
}
