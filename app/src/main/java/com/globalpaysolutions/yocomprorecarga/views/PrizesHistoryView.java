package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.Prize;

import java.util.List;

/**
 * Created by Josué Chávez on 20/07/2017.
 */

public interface PrizesHistoryView
{
    void initializeViews();
    void renderPrizes(List<Prize> prizes);
    void showGenericToast(String content);
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void showGenericDialog(DialogViewModel dialogModel);
}
