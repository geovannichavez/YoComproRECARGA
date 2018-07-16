package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.models.api.New;

import java.util.List;

public interface NewsView
{
    void initialize();
    void renderNews(List<New> response);
    void showLoadingDialog(String label);
    void hideLoadingDialog();
    void showGenericDialog(DialogViewModel dialog);
}
