package com.globalpaysolutions.yocomprorecarga.views;

import android.content.DialogInterface;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;

public interface CompleteProfileView
{
    void initialViewsState();
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void showGenericDialog(DialogViewModel messageModel, DialogInterface.OnClickListener clickListener);
    void navigateMain();
    void validateNickname(String firstname, String lastname, String nick);
}
