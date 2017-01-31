package com.globalpaysolutions.yocomprorecarga.views;

import com.globalpaysolutions.yocomprorecarga.models.Amount;
import com.globalpaysolutions.yocomprorecarga.models.CountryOperator;
import com.globalpaysolutions.yocomprorecarga.models.DialogViewModel;
import com.globalpaysolutions.yocomprorecarga.ui.fragments.CustomDialogFragment;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Josué Chávez on 16/01/2017.
 */

public interface RequestTopupView
{
    void renderOperators(List<CountryOperator> countryOperators);
    void initialViewsState();
    void showAmounts(List<String> pOperatorNames, HashMap<String, Amount> pOperatorsMap);
    void setSelectedOperator(int pPosition);
    void resetAmount();
    void showGenericMessage(DialogViewModel pMessageModel);
    void showLoading(String pLabel);
    void hideLoading();
    void showSuccessMessage(DialogViewModel pMessageModel);
    void showErrorMessage(DialogViewModel pMessageModel);
}
