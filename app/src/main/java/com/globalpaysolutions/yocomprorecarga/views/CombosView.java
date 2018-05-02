package com.globalpaysolutions.yocomprorecarga.views;

import android.view.View;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.Combo;

import java.util.List;

/**
 * Created by Josué Chávez on 16/01/2018.
 */

public interface CombosView
{
    void initialViews();
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void renderCombos(List<Combo> combos);
    void showGenericImageDialog(DialogViewModel dialog, int resource);
    void showExchangeConfirmDialog(DialogViewModel dialogContent, int resource, View.OnClickListener onClickListener);
    void hideConfirmDialog();
    void navigatePrizeDetails();
}
