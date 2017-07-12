package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;

/**
 * Created by Josué Chávez on 28/06/2017.
 */

public interface AuthenticateView
{
    void navigateValidatePhone();
    void navigateSetNickname();
    void navigateHome();
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void showGenericDialog(DialogViewModel messageModel);
    void showGenericToast(String text);
}
