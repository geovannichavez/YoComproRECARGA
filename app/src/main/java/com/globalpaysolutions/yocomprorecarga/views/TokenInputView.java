package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.ErrorResponseViewModel;

/**
 * Created by Josué Chávez on 18/01/2017.
 */

public interface TokenInputView
{
    void initialViewsState();
    void setClickListeners();
    void showLoading();
    void dismissLoading();
    void showErrorMessage(ErrorResponseViewModel pErrorMessage);
    void showSucceesTokenValidation();
    void navigateHome(boolean p3DCompatible);
    void vibrateOnSuccess();
    void cleanFields();
    void setCallcenterContactText();
    void setCodeSentLabelText(String phoneNumber);
    void navigatePhoneValidation(boolean retypePhone);
}
