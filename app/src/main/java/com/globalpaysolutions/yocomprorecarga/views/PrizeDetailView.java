package com.globalpaysolutions.yocomprorecarga.views;

import android.os.Bundle;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;

/**
 * Created by Josué Chávez on 17/07/2017.
 */

public interface PrizeDetailView
{
    void updateViews(Bundle data);
    void showGenericToast(String message);
    void showGenericDialog(DialogViewModel dialogViewModel);
}
