package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.Countries;
import com.globalpaysolutions.yocomprorecarga.models.ErrorResponseViewModel;

/**
 * Created by Josué Chávez on 13/01/2017.
 */

public interface ValidatePhoneView
{
    void initialViewsStates();
    void showLoading();
    void hideLoading();
    void showErrorMessage(ErrorResponseViewModel pErrorMessage);
    void renderCountries(Countries pCountries);
    void navigateTokenInput();
}
