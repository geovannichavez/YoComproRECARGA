package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;

/**
 * Created by Josué Chávez on 18/11/2017.
 */

public interface WildcardView
{
    void initialViewsState(String urlMain);
    void changeWinnerView(String coins, String urlWin);
    void changeLoserView(String coins, String urlLoser);
    void navigateToPrizeDetail();
    void showImageDialog(DialogViewModel dialogModel, int resource);
    void showCloseableDialog(DialogViewModel dialogModel, int resource);
    void showLoadingDialog(String label);
    void dismissLoadingDialog();
}
