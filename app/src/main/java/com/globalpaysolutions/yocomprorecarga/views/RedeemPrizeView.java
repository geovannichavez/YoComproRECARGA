package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;

/**
 * Created by Josué Chávez on 17/11/2017.
 */

public interface RedeemPrizeView
{
    void initializeViews();
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void createImageDialog(DialogViewModel dialog, int resource);
}
